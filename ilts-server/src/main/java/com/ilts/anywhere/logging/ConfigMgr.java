/*
 * Copyright (c) 2010-2016 International Lottery & Totalizator Systems, Inc.
 *
 * File: ConfigMgr.java
 */
package com.ilts.anywhere.logging;

import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/**
 *
 * @author jnguyen
 */
public class ConfigMgr {
    @Context
    private ServletContext _context;
    private ServletConfig _config;
    private Properties _properties;

    /**
     * Constructor - ConfigMgr
     * @param wsctx
     */
    public ConfigMgr(WebServiceContext wsctx) {
        this._context = (ServletContext) wsctx.getMessageContext().get(MessageContext.SERVLET_CONTEXT);
        this._config = null;
        this._properties = null;
    }

    /**
     * Constructor - ConfigMgr
     * @param srvltconfig
     */
    public ConfigMgr(ServletConfig srvltconfig) {
        this._context = null;
        this._config = srvltconfig;
        this._properties = null;
    }

    /**
     * Constructor - ConfigMgr
     * @param srvltcontxt
     */
    public ConfigMgr(ServletContext srvltcontxt) {
        this._context = srvltcontxt;
        this._config = null;
        this._properties = null;
    }

    /**
     * Constructor - ConfigMgr
     * @param properties
     */
    public ConfigMgr(Properties properties) {
        this._context = null;
        this._config = null;
        this._properties = properties;
    }

    /**
     * Function - getConfigParamValue
     * @param paramName
     * @return configuration parameter value (from web.xml)
     */
    public String getConfigParamValue(String paramName) {
        String paramValue = "";

        if (this._context != null) {
            paramValue = this._context.getInitParameter(paramName);
        }
        if (this._config != null) {
            paramValue = this._config.getInitParameter(paramName);
        }
        if (this._properties != null) {
            paramValue = this._properties.getProperty(paramName);
        }

        return paramValue;
    }

    /**
     * Function - reload
     * @param srvltconfig
     */
    public void reload(ServletConfig srvltconfig) {
        this._context = null;

        this._config = null;
        this._config = srvltconfig;

        this._properties = null;
    }

    /**
     * Function - reload
     * @param srvltcontxt
     */
    public void reload(ServletContext srvltcontxt) {
        this._context = null;
        this._context = srvltcontxt;

        this._config = null;
        this._properties = null;
    }

    /**
     * Function - reload
     * @param properties
     */
    public void reload(Properties properties) {
        this._context = null;
        this._config = null;

        this._properties.clear();
        this._properties = properties;
    }
}