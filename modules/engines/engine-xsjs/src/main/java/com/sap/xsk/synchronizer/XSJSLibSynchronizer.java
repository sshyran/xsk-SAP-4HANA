/*
 * Copyright (c) 2022 SAP SE or an SAP affiliate company and XSK contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, v2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * SPDX-FileCopyrightText: 2022 SAP SE or an SAP affiliate company and XSK contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sap.xsk.synchronizer;

import com.sap.xsk.exceptions.XSJSLibExportsGenerationSourceNotFoundException;
import com.sap.xsk.utils.XSKCommonsConstants;
import org.apache.commons.io.IOUtils;
import org.eclipse.dirigible.commons.api.context.ThreadContextFacade;
import org.eclipse.dirigible.commons.api.scripting.ScriptingException;
import org.eclipse.dirigible.core.scheduler.api.AbstractSynchronizer;
import org.eclipse.dirigible.core.scheduler.api.IOrderedSynchronizerContribution;
import org.eclipse.dirigible.engine.api.script.ScriptEngineExecutorsManager;
import org.eclipse.dirigible.engine.js.graalvm.processor.GraalVMJavascriptEngineExecutor;
import org.eclipse.dirigible.repository.api.IResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class XSJSLibSynchronizer extends AbstractSynchronizer implements IOrderedSynchronizerContribution {

  private static final Logger logger = LoggerFactory.getLogger(XSJSLibSynchronizer.class);

  private static final String XSJSLIB_RUN_GENERATION_LOCATION = "/exports/XSJSLibRunGeneration.mjs";

  private final String targetLocation;

  public XSJSLibSynchronizer() {
    this(XSKCommonsConstants.XSK_REGISTRY_PUBLIC);
  }

  public XSJSLibSynchronizer(String targetLocation) {
    this.targetLocation = targetLocation;
  }

  public static void forceSynchronization(String targetLocation) {
    XSJSLibSynchronizer synchronizer = new XSJSLibSynchronizer(targetLocation);
    synchronizer.setForcedSynchronization(true);
    try {
      synchronizer.synchronize();
    } finally {
      synchronizer.setForcedSynchronization(false);
    }
  }

  @Override
  protected void synchronizeResource(IResource iResource) {
    logger.trace("Synchronizing XSJSLib Resource...");
    // TODO: What?
  }

  @Override
  public int getPriority() {
    return 666;
  }

  @Override
  public void synchronize() {
    synchronized (XSJSLibSynchronizer.class) {
      if(beforeSynchronizing()) {
        logger.trace("Synchronizing XSJSLibs...");

//        try {
//          ThreadContextFacade.setUp();

          Map<Object, Object> context = new HashMap<>();
          context.put("registry_path", targetLocation);

          GraalVMJavascriptEngineExecutor graalVMJavascriptEngineExecutor = new GraalVMJavascriptEngineExecutor();
          graalVMJavascriptEngineExecutor.executeService(
              XSJSLIB_RUN_GENERATION_LOCATION,
              context,
              true,
              false
          );
//        } finally {
//          ThreadContextFacade.tearDown();
//        }

        logger.trace("Done synchronizing XSJSLibs.");
        afterSynchronizing();
      }
    }
  }
}
