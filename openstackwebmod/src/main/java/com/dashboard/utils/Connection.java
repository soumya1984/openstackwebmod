/*
 * Purpose : defines Connection Object for the Openstack connection
 */

package com.dashboard.utils;

import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;

public class Connection {
	public ComputeService compute;
	public ComputeServiceContext context;
}
