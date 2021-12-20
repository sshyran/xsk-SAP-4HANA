/*
 * Copyright (c) 2021 SAP SE or an SAP affiliate company and XSK contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, v2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * SPDX-FileCopyrightText: 2021 SAP SE or an SAP affiliate company and XSK contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sap.xsk.xsodata.ds.service;

import com.sap.xsk.utils.XSKCommonsConstants;
import com.sap.xsk.utils.XSKCommonsUtils;
import java.sql.SQLException;
import org.eclipse.dirigible.engine.odata2.definition.ODataDefinition;
import org.eclipse.dirigible.engine.odata2.transformers.OData2ODataXTransformer;

public class XSKOData2ODataXTransformer {

  private OData2ODataXTransformer oData2ODataXTransformer = new OData2ODataXTransformer(new XSKTableMetadataProvider());

  public String[] transform(ODataDefinition oDataDefinition) throws SQLException {
    try {
      return oData2ODataXTransformer.transform(oDataDefinition);
    } catch (Exception e) {
      XSKCommonsUtils.logProcessorErrors(e.getMessage(), XSKCommonsConstants.PROCESSOR_ERROR, oDataDefinition.getLocation(), XSKCommonsConstants.XSK_ODATA_PARSER);
      throw e;
    }
  }
}