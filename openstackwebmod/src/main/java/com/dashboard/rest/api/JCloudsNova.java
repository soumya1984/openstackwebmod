package com.dashboard.rest.api;

import java.io.Closeable;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import com.google.common.collect.ImmutableSet;
import com.google.common.io.Closeables;
import com.google.inject.Module;

public class JCloudsNova implements Closeable {
    private final NovaApi novaApi;
    private final Set<String> zones;

    public static void main(String[] args) throws IOException {
        JCloudsNova jcloudsNova = new JCloudsNova();

        try {
            jcloudsNova.listServers();
            jcloudsNova.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jcloudsNova.close();
        }
    }

    public JCloudsNova() {
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());

        String provider = "openstack-nova";
        String identity = "admin:admin"; // tenantName:userName
        String credential = "01ff16bf4cbb4020";
        
        Properties overrides = new Properties();
        //overrides.setProperty(KeystoneProperties.CREDENTIAL_TYPE, CredentialTypes.PASSWORD_CREDENTIALS);
        // .endpoint("http://192.168.56.1:5000/v2.0/")
        overrides.setProperty(org.jclouds.Constants.PROPERTY_API_VERSION, "2");
        overrides.setProperty(org.jclouds.Constants.PROPERTY_ENDPOINT, "http://192.168.56.1:5000/v2.0/");
        overrides.setProperty(org.jclouds.Constants.PROPERTY_CREDENTIAL, credential);
        overrides.setProperty(org.jclouds.Constants.PROPERTY_IDENTITY, identity);
        overrides.setProperty(org.jclouds.Constants.PROPERTY_PROVIDER,provider);
        //overrides.setProperty(org.jclouds.Constants., arg1)

        novaApi = ContextBuilder.newBuilder(provider)
                .modules(modules).overrides(overrides)
                .buildApi(NovaApi.class);
        
        zones = novaApi.getConfiguredZones();
    }

    private void listServers() {
        for (String zone : zones) {
            ServerApi serverApi = novaApi.getServerApiForZone(zone);

            System.out.println("Servers in " + zone);
            serverApi.getMetadata(serverApi.toString());
            for (Server server : serverApi.listInDetail().concat()) {
                System.out.println("  " + server);
            }
        }
    }

    public void close() throws IOException {
        Closeables.close(novaApi, true);
    }
}