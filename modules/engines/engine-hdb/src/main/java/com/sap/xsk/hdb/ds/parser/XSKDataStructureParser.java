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
package com.sap.xsk.hdb.ds.parser;

import java.io.IOException;

import com.sap.xsk.exceptions.XSKArtifactParserException;
import com.sap.xsk.hdb.ds.api.XSKDataStructuresException;
import com.sap.xsk.hdb.ds.model.XSKDataStructureModel;
import org.eclipse.dirigible.core.problems.exceptions.ProblemsException;

public interface XSKDataStructureParser<T extends XSKDataStructureModel> {

  T parse(String location, String content) throws XSKDataStructuresException, IOException, XSKArtifactParserException, ProblemsException;

  String getType();

  Class<T> getDataStructureClass();
}