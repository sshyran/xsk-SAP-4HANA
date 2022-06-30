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
package com.sap.xsk.xsodata.ds.handler;

import com.google.gson.internal.LinkedTreeMap;
import com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.edm.EdmException;
import org.apache.olingo.odata2.api.edm.EdmType;
import org.apache.olingo.odata2.api.ep.EntityProviderException;
import org.apache.olingo.odata2.api.ep.entry.ODataEntry;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.apache.olingo.odata2.api.uri.UriInfo;
import org.apache.olingo.odata2.core.ep.BasicEntityProvider;
import org.eclipse.dirigible.commons.api.helpers.GsonHelper;
import org.eclipse.dirigible.commons.config.StaticObjects;
import org.eclipse.dirigible.engine.odata2.api.ODataException;
import org.eclipse.dirigible.engine.odata2.definition.ODataHandlerDefinition;
import org.eclipse.dirigible.engine.odata2.service.ODataCoreService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.AFTER_TABLE_NAME;
import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.BEFORE_DELETE_ENTITY_TABLE_NAME;
import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.BEFORE_TABLE_NAME;
import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.BEFORE_UPDATE_ENTITY_TABLE_NAME;
import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.CONNECTION;
import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.ENTRY_MAP;
import static com.sap.xsk.xsodata.utils.XSKOData2EventHandlerUtils.ON_CREATE_ENTITY_TABLE_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class XSKOData2EventHandlerTest {

  @Mock
  private EdmType edmType;

  @Mock
  private UriInfo uriInfo;

  @Mock
  private ODataCoreService odataCoreService;

  @Mock
  private DataSource dataSource;

  @Mock
  private ODataEntry entry;

  @Mock
  private InputStream inputStream;

  private static MockedStatic<XSKOData2EventHandlerUtils> handlerUtils;

  private static MockedStatic<StaticObjects> staticObjects;

  private XSKScriptingOData2EventHandler spyScriptingHandler;
  private XSKProcedureOData2EventHandler spyProcedureHandler;
  private XSKOData2EventHandler xskoData2EventHandler;
  private Map<Object, Object> scriptingHandlerContext;
  private Map<Object, Object> procedureHandlerContext;

  @Before
  public void setup() {
    handlerUtils = Mockito.mockStatic(XSKOData2EventHandlerUtils.class);
    staticObjects = Mockito.mockStatic(StaticObjects.class);
    staticObjects.when(() -> StaticObjects.get(StaticObjects.DATASOURCE)).thenReturn(dataSource, dataSource);
    spyScriptingHandler = Mockito.spy(new XSKScriptingOData2EventHandler());
    spyProcedureHandler = Mockito.spy(new XSKProcedureOData2EventHandler());
    xskoData2EventHandler = new XSKOData2EventHandler(odataCoreService, spyProcedureHandler, spyScriptingHandler);
    scriptingHandlerContext = new HashMap();
    procedureHandlerContext = new HashMap();
  }

  @After
  public void teardown() {
    handlerUtils.close();
    staticObjects.close();
  }

  @Test
  public void testBeforeCreateEntity() throws org.apache.olingo.odata2.api.exception.ODataException, ODataException, SQLException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getInsertTargetTableName(), false);
    xskoData2EventHandler.beforeCreateEntity(uriInfo, "application/json", "application/json", entry,
        procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doNothing().when(spyScriptingHandler).callSuperBeforeCreateEntity(any(), any(), any(), any(), any());
    xskoData2EventHandler.beforeCreateEntity(uriInfo, "application/json", "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey("connection"));
    assertTrue(scriptingHandlerContext.containsKey("afterTableName"));
  }

  @Test
  public void testAfterCreateEntity() throws ODataException, EdmException, SQLException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getInsertTargetTableName(), false);
    xskoData2EventHandler.afterCreateEntity(uriInfo, "application/json", "application/json", entry,
        procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doNothing().when(spyScriptingHandler).callSuperAfterCreateEntity(any(), any(), any(), any(), any());
    xskoData2EventHandler.afterCreateEntity(uriInfo, "application/json", "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME));
  }

  @Test
  public void testOnCreateEntity() throws ODataException, EdmException, SQLException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getInsertTargetTableName(), false);
    xskoData2EventHandler.onCreateEntity(uriInfo, inputStream, "application/json", "application/json",
        procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doReturn(null).when(spyScriptingHandler).callSuperOnCreateEntity(any(), any(), any(), any(), any());
    xskoData2EventHandler.onCreateEntity(uriInfo, inputStream, "application/json", "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(ON_CREATE_ENTITY_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(ENTRY_MAP));
  }

  @Test
  public void testBeforeUpdateEntity() throws ODataException, EdmException, SQLException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getUpdateTargetTableName(), true);
    xskoData2EventHandler.beforeUpdateEntity(uriInfo, "application/json", true, "application/json", entry,
        procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doNothing().when(spyScriptingHandler).callSuperBeforeUpdateEntity(any(), any(), anyBoolean(), any(), any(), any());
    xskoData2EventHandler.beforeUpdateEntity(uriInfo, "application/json", true, "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_UPDATE_ENTITY_TABLE_NAME));
  }

  @Test
  public void testAfterUpdateEntity() throws SQLException, ODataException, EdmException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getUpdateTargetTableName(), true);
    xskoData2EventHandler.afterUpdateEntity(uriInfo, "application/json", true, "application/json", entry,
        procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doNothing().when(spyScriptingHandler).callSuperAfterUpdateEntity(any(), any(), anyBoolean(), any(), any(), any());
    xskoData2EventHandler.afterUpdateEntity(uriInfo, "application/json", true, "application/json", entry, scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME));
  }

  @Test
  public void testOnUpdateEntity() throws SQLException, ODataException, EdmException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getUpdateTargetTableName(), true);
    xskoData2EventHandler.onUpdateEntity(uriInfo, inputStream, "application/json", true, "application/json",
        procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doReturn(null).when(spyScriptingHandler).callSuperOnUpdateEntity(any(), any(), any(), anyBoolean(), any(), any());
    xskoData2EventHandler.onUpdateEntity(uriInfo, inputStream, "application/json", true, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(AFTER_TABLE_NAME));
  }

  @Test
  public void testBeforeDeleteEntity() throws SQLException, ODataException, EdmException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getDeleteTargetTableName(), false);
    xskoData2EventHandler.beforeDeleteEntity(uriInfo, "application/json", procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doNothing().when(spyScriptingHandler).callSuperBeforeDeleteEntity(any(), any(), any());
    xskoData2EventHandler.beforeDeleteEntity(uriInfo, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_DELETE_ENTITY_TABLE_NAME));
  }

  @Test
  public void testAfterDeleteEntity() throws SQLException, ODataException, EdmException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getDeleteTargetTableName(), false);
    xskoData2EventHandler.afterDeleteEntity(uriInfo, "application/json", procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doNothing().when(spyScriptingHandler).callSuperAfterDeleteEntity(any(), any(), any());
    xskoData2EventHandler.afterDeleteEntity(uriInfo, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME));
  }

  @Test
  public void testOnDeleteEntity() throws SQLException, ODataException, EdmException, EntityProviderException {
    // procedure
    mockGetHandlers();
    mockCallProcedure(spyProcedureHandler, getDeleteTargetTableName(), false);
    xskoData2EventHandler.onDeleteEntity(uriInfo, "application/json", procedureHandlerContext);
    assertResponse(procedureHandlerContext);

    // .xsjslib
    Mockito.doReturn(null).when(spyScriptingHandler).callSuperOnDeleteEntity(any(), any(), any());
    xskoData2EventHandler.onDeleteEntity(uriInfo, "application/json", scriptingHandlerContext);
    assertTrue(scriptingHandlerContext.containsKey(CONNECTION));
    assertTrue(scriptingHandlerContext.containsKey(BEFORE_TABLE_NAME));
  }

  private void mockGetHandlers() throws EdmException, ODataException {
    Mockito.when(uriInfo.getTargetType()).thenReturn(edmType, edmType);
    Mockito.when(edmType.getNamespace()).thenReturn("test-namespace", "test-namespace");
    Mockito.when(edmType.getName()).thenReturn("test-name", "test-name");

    ODataHandlerDefinition procedureHandler = new ODataHandlerDefinition();
    procedureHandler.setHandler("test-namespace::procedure");
    List<ODataHandlerDefinition> procedureHandlers = List.of(procedureHandler);
    ODataHandlerDefinition scriptingHandler = new ODataHandlerDefinition();
    scriptingHandler.setHandler("test-namespace::handler.xsjslib::handlerFunction");
    List<ODataHandlerDefinition> scriptingHandlers = List.of(scriptingHandler);
    Mockito.when(odataCoreService.getHandlers(any(), any(), any(), any()))
        .thenReturn(procedureHandlers, scriptingHandlers);
  }

  private void mockCallProcedure(XSKProcedureOData2EventHandler handler, Runnable getBuilderTargetTable, boolean isUpdate)
      throws SQLException {
    handlerUtils.when(() -> getBuilderTargetTable.run()).thenReturn("test-table", "test-table");

    Mockito.doReturn("TEST_SCHEMA").when(handler).getODataArtifactTypeSchema("test-table");

    ResultSet resultSetMock = Mockito.mock(ResultSet.class);
    Mockito.when(resultSetMock.next()).thenReturn(true);
    Mockito.when(resultSetMock.getString(anyString())).thenReturn("400", "INVALID ID");
    Mockito.when(resultSetMock.getString(anyInt())).thenReturn("detail message 1", "detail message 2");

    ResultSetMetaData metaData = Mockito.mock(ResultSetMetaData.class);

    Mockito.when(metaData.getColumnCount()).thenReturn(2);
    Mockito.when(metaData.getColumnName(anyInt())).thenReturn("errorDetail1", "errorDetail2");

    Mockito.when(resultSetMock.getMetaData()).thenReturn(metaData);

    if (isUpdate) {
      Mockito.doReturn(resultSetMock).when(handler).callProcedure(any(), any(), any(), any(), any());
    } else {
      Mockito.doReturn(resultSetMock).when(handler).callProcedure(any(), any(), any(), any());
    }

    handlerUtils.when(() -> XSKOData2EventHandlerUtils.createErrorDocument(resultSetMock)).thenCallRealMethod();
  }

  private void assertResponse(Map<Object, Object> context) throws EntityProviderException {
    assertTrue(context.containsKey("errorDocument"));

    ODataResponse response = (ODataResponse) context.get("errorDocument");

    assertEquals(response.getStatus(), HttpStatusCodes.BAD_REQUEST);
    assertEquals("application/json", response.getHeader("Content-Type"));

    Map<String, Object> responseBody = GsonHelper.GSON.fromJson(new BasicEntityProvider().readText(((InputStream) response.getEntity())),
        HashMap.class);

    assertEquals("INVALID ID", ((LinkedTreeMap) ((LinkedTreeMap) responseBody.get("error")).get("message")).get("value"));
    assertEquals("{\"errordetail\":{\"errorDetail2\":\"detail message 2\",\"errorDetail1\":\"detail message 1\"}}",
        (((LinkedTreeMap) responseBody.get("error")).get("innererror")));
  }

  private Runnable getUpdateTargetTableName() {
    return () -> {
      try {
        XSKOData2EventHandlerUtils.getSQLUpdateBuilderTargetTable(any(), any());
      } catch (org.apache.olingo.odata2.api.exception.ODataException e) {
        fail();
      }
    };
  }

  private Runnable getInsertTargetTableName() {
    return () -> {
      try {
        XSKOData2EventHandlerUtils.getSQLInsertBuilderTargetTable(any(), any());
      } catch (org.apache.olingo.odata2.api.exception.ODataException e) {
        fail();
      }
    };
  }

  private Runnable getDeleteTargetTableName() {
    return () -> {
      try {
        XSKOData2EventHandlerUtils.getSQLDeleteBuilderTargetTable(any(), any());
      } catch (org.apache.olingo.odata2.api.exception.ODataException e) {
        fail();
      }
    };
  }

}