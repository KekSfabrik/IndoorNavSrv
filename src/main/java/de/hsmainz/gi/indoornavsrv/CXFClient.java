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

import de.hsmainz.gi.types.Beacon;
import de.hsmainz.gi.indoornavsrv.services.IBeaconLocatorService;
import de.hsmainz.gi.indoornavsrv.services.IBeaconPositionerService;
import de.hsmainz.gi.indoornavsrv.services.security.ClientPasswordCallback;
import de.hsmainz.gi.indoornavsrv.util.StringUtils;
import de.hsmainz.gi.types.Location;
import de.hsmainz.gi.types.LocationId;
import de.hsmainz.gi.types.Site;
import de.hsmainz.gi.types.WkbPoint;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.log4j.PropertyConfigurator;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
//import org.apache.ws.security.WSConstants;
//import org.apache.ws.security.handler.WSHandlerConstants;import org.slf4j.Logger;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;

/**
 * Startup Class for the WebServices Client. Allows for testing of the accompanying
 * Server {@link de.hsmainz.gi.indoornavsrv.CXFServer}.
 * 
 * 
 * 
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>,
 * 23.01.2015
 */
public class CXFClient {

    private static final Logger L = new Log4jLoggerFactory().getLogger(CXFClient.class.getSimpleName());
    
    public static void main(String args[]) throws Exception {
        System.out.println("Connecting to:");
        System.out.println("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconLocatorService");
        System.out.println("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconPositionerService (requires username & password)");
        IBeaconLocatorService locatorClient = createBeaconLocatorServiceClient();
        IBeaconPositionerService positionerClient = createBeaconPositionerServiceClient();
        
        // do something here
        // like read from the locatorClient or positionerClient
//        L.info("trying to addBeacon");
//        positionerClient.addBeacon("00000000000000000000000000000000", 100, 1);
//        
//        
//        Beacon beacon = locatorClient.getBeacon("00000000000000000000000000000000", 100, 1);
//        L.info("get Beacon #1:\t"+beacon);
//        beacon = locatorClient.getBeacon("00000000000000000000000000000000", 100, 12);
//        L.info("get Beacon #2:\t"+beacon);
//        
//        beacon = locatorClient.getBeacon("00000000000000000000000000000000", 100, 12);
//        L.info("get Beacon #1 again:\t"+beacon);
//        L.info("trying to deleteBeacon");
//        positionerClient.deleteBeacon(beacon);
        
        List<Beacon> beacons = new ArrayList<>();
        beacons.add(new Beacon(0, "00000000000000000000000000000000", 100, 12));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 14047));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15873));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15874));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15884));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 15893));
        beacons.add(new Beacon(0, "a0b137303a9a11e3aa6e0800200c9a66", 32895, 16027));
        beacons.add(new Beacon(0, "b9407f30f5f8466eaff925556b57fe6d", 102, 5));
        beacons.add(new Beacon(0, "b9407f30f5f8466eaff925556b57fe6d", 102, 6));
        L.info("input\t" + Arrays.toString(StringUtils.listAll(beacons)));
        List<Beacon> realBeacons = locatorClient.getBeacons(beacons);
        L.info("output\t" + Arrays.toString(StringUtils.listAll(realBeacons)));
        
        Beacon     testBeacon = new Beacon(1, "00000000000000000000000000000000", 100, 12);
        Site       testSite = new Site(1, "KekSfabrik - Lotharstrasse 1,  55116 Mainz");
        
        WkbPoint expResult = new WkbPoint(new com.vividsolutions.jts.geom.GeometryFactory()
                        .createPoint(
                            new com.vividsolutions.jts.geom.Coordinate(
                                0, 0, 1
                            )
                        ));
        WkbPoint result = locatorClient.getCoordinate(testSite, testBeacon);
        L.info("result:\t" + result.getWkb() + "\t" + result.getPoint());
        L.info("should:\t" + expResult.getWkb() + "\t" + expResult.getPoint());

        System.exit(0);
    }

    private static IBeaconLocatorService createBeaconLocatorServiceClient() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(IBeaconLocatorService.class);
        factory.setAddress("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconLocatorService");
        IBeaconLocatorService service = (IBeaconLocatorService) factory.create();
//        Client client = ClientProxy.getClient(service);
//        Endpoint cxfEndpoint = client.getEndpoint();
//        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor();
//        cxfEndpoint.getOutInterceptors().add(wssOut);
        return service;
    }

    private static IBeaconPositionerService createBeaconPositionerServiceClient() {
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.getInInterceptors().add(new LoggingInInterceptor());
        factory.getOutInterceptors().add(new LoggingOutInterceptor());
        factory.setServiceClass(IBeaconPositionerService.class);
        factory.setAddress("http://"+CXFServer.hostname+":"+CXFServer.port+"/service/BeaconPositionerService");
        IBeaconPositionerService service = (IBeaconPositionerService) factory.create();
        Client client = ClientProxy.getClient(service);
        Endpoint cxfEndpoint = client.getEndpoint();
        Map<String, Object> props = new HashMap<>();
        props.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
        // Specify our username
        props.put(WSHandlerConstants.USER, "indoornav-admin");
        // Password type : plain text
        props.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);
        // for hashed password use:
        //properties.setProperty(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_DIGEST);
        // Callback used to retrive password for given user.
        props.put(WSHandlerConstants.PW_CALLBACK_CLASS, ClientPasswordCallback.class.getName());
        WSS4JOutInterceptor wssOut = new WSS4JOutInterceptor(props);
        cxfEndpoint.getOutInterceptors().add(wssOut);
        return service;
    }
}
