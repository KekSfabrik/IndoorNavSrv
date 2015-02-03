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

package de.hsmainz.gi.indoornavsrv.services.security;

import de.hsmainz.gi.indoornavsrv.CXFClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.impl.Log4jLoggerFactory;
//import org.apache.ws.security.WSPasswordCallback;
//import org.apache.ws.security.WSSecurityException;

/**
 *
 * @author Jan "KekS" M. <a href="mailto:keks@keksfabrik.eu">mail</a>, 21.01.2015
 */
public final class ServerPasswordCallback implements CallbackHandler {

    private static final Logger L = new Log4jLoggerFactory().getLogger(CXFClient.class.getSimpleName());
    
    //this can be moved to a resource bundle
    private static String username;
    private static String password;
    
    static {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("user.properties"));
            username = props.get("admin.username").toString();
            password = props.get("admin.password").toString();
        } catch (IOException ex) {
            L.error("Could not get Username and Password for Adminuser.", ex);
            username = null;
            password = null;
        }
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        String strClientPwd = pc.getPassword();
        int usage = pc.getUsage();
        if (pc.getIdentifier().equals(username)) {
            if (usage == WSPasswordCallback.USERNAME_TOKEN) { //PasswordDigest
                pc.setPassword(password);
            } 
            else if (usage == WSPasswordCallback.USERNAME_TOKEN_UNKNOWN) { //PasswordText
                pc.setPassword(password);
                if(!strClientPwd.equals(password)){ //DIY compare
                    throw new UnsupportedCallbackException(pc, "Invalid user/password combination.");
                    //throw new WSSecurityException(WSSecurityException.FAILED_AUTHENTICATION);
                }
            }
        }
    }
}