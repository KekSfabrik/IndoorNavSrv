/*
 * Copyright (C) 2015 Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package de.hsmainz.gi.indoornavsrv;

import de.hsmainz.gi.indoornavsrv.services.BeaconLocatorService;
import de.hsmainz.gi.indoornavsrv.services.BeaconPositionerService;
import de.hsmainz.gi.indoornavsrv.services.security.ServerPasswordCallback;
import java.util.HashMap;
import java.util.Map;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
//import org.apache.ws.security.WSConstants;
//import org.apache.ws.security.handler.WSHandlerConstants;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>,
 * 21.01.2015
 */
public class CXFServer {
    
//    private static String   hostname = "192.168.1.36";
    public static String   hostname = "localhost";
//    public static String   hostname = "143.93.114.129";
    public static Integer  port = 8080;

    public static void main(String... args) throws Exception {
        
        parseArgs(args);
        
        BeaconLocatorService    locatorService = new BeaconLocatorService();
        BeaconPositionerService positionerService = new BeaconPositionerService();
        
        createLocatorService(locatorService);
        createPositionerService(positionerService);
        
        System.out.println("Server running at:");
        System.out.println("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconLocatorService");
        System.out.println("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconPositionerService (requires username & password)");
    }

    private static void createLocatorService(Object serviceObj) {
        JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
        serverFactory.setServiceClass(BeaconLocatorService.class);
        serverFactory.setAddress("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconLocatorService");
        serverFactory.setServiceBean(serviceObj);
        Server server = serverFactory.create();
        Endpoint cxfEndpoint = server.getEndpoint();
        cxfEndpoint.getInInterceptors().add(new LoggingInInterceptor());
        cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
    }


    private static void createPositionerService(Object serviceObj) {
        JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
        serverFactory.setServiceClass(BeaconPositionerService.class);
        serverFactory.setAddress("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconPositionerService");
        serverFactory.setServiceBean(serviceObj);
        Server server = serverFactory.create();
        Endpoint cxfEndpoint = server.getEndpoint();
        cxfEndpoint.getInInterceptors().add(new LoggingInInterceptor());
        cxfEndpoint.getOutInterceptors().add(new LoggingOutInterceptor());
        Map<String, Object> props = new HashMap<>();
        props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        // for hashed password use:
        //properties.setProperty(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // Callback used to retrive password for given user.
        props.put(WSHandlerConstants.PW_CALLBACK_CLASS, ServerPasswordCallback.class.getName());
        WSS4JInInterceptor wssIn = new WSS4JInInterceptor(props);
        cxfEndpoint.getInInterceptors().add(wssIn);
    }
    
    private static void parseArgs(String... args) {
        System.out.print("Starting Server ");
        if (args.length > 0) {
            System.out.println("with custom input:");
            if (args.length > 1) {
                try {
                    port = Integer.parseInt(args[0]);
                    System.out.println("host\tlocalhost\nport\t"+port);
                } catch (NumberFormatException ex) {
                    hostname = args[0];
                    System.out.println("host\t"+hostname);
                    if (args.length >= 2)
                        port = Integer.parseInt(args[1]);
                    System.out.println("host\t"+port);
                }
            }
        }
        else {
            System.out.println("with default values.");
        }
    }
}
