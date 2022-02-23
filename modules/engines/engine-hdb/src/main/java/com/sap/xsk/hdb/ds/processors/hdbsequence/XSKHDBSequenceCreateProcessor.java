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
package com.sap.xsk.hdb.ds.processors.hdbsequence;

import com.sap.xsk.hdb.ds.artefacts.HDBSequenceSynchronizationArtefactType;
import com.sap.xsk.hdb.ds.model.hdbsequence.XSKDataStructureHDBSequenceModel;
import com.sap.xsk.hdb.ds.processors.AbstractXSKProcessor;
import com.sap.xsk.utils.XSKCommonsConstants;
import com.sap.xsk.utils.XSKCommonsUtils;
import com.sap.xsk.utils.XSKConstants;
import com.sap.xsk.utils.XSKHDBUtils;
import java.sql.Connection;
import java.sql.SQLException;
import org.eclipse.dirigible.core.scheduler.api.ISynchronizerArtefactType.ArtefactState;
import org.eclipse.dirigible.database.sql.ISqlDialect;
import org.eclipse.dirigible.database.sql.SqlFactory;
import org.eclipse.dirigible.database.sql.dialects.hana.HanaSqlDialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XSKHDBSequenceCreateProcessor extends AbstractXSKProcessor<XSKDataStructureHDBSequenceModel> {


  private static final Logger logger = LoggerFactory.getLogger(XSKHDBSequenceCreateProcessor.class);
  private static final HDBSequenceSynchronizationArtefactType SEQUENCE_ARTEFACT = new HDBSequenceSynchronizationArtefactType();

  @Override
  public void execute(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel) throws SQLException {
    String hdbSequenceName = XSKHDBUtils.escapeArtifactName(hdbSequenceModel.getName(), hdbSequenceModel.getSchema());
    logger.info("Processing Create HdbSequence: " + hdbSequenceName);
    String sql = null;
    switch (hdbSequenceModel.getDBContentType()) {
      case XS_CLASSIC: {
        sql = getDatabaseSpecificSQL(connection, hdbSequenceModel, hdbSequenceName);
        break;
      }
      case OTHERS: {
        ISqlDialect dialect = SqlFactory.deriveDialect(connection);
        if (dialect.getClass().equals(HanaSqlDialect.class)) {
          sql = XSKConstants.XSK_HDBSEQUENCE_CREATE + hdbSequenceModel.getRawContent();
          break;
        } else {
          String errorMessage = String.format("Sequences are not supported for %s !", dialect.getDatabaseName(connection));
          XSKCommonsUtils.logProcessorErrors(errorMessage, XSKCommonsConstants.PROCESSOR_ERROR, hdbSequenceModel.getLocation(),
              XSKCommonsConstants.HDB_SEQUENCE_PARSER);
          applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_CREATE,
              errorMessage);
          throw new IllegalStateException(errorMessage);
        }
      }
    }
    try {
      executeSql(sql, connection);
      String message = String.format("Create sequence %s successfully", hdbSequenceModel.getName());
      applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.SUCCESSFUL_CREATE,
          message);
    } catch (SQLException ex) {
      String message = String.format("Create sequence [%s] skipped due to an error: %s", hdbSequenceModel.getName(), ex.getMessage());
      XSKCommonsUtils.logProcessorErrors(ex.getMessage(), XSKCommonsConstants.PROCESSOR_ERROR, hdbSequenceModel.getLocation(),
          XSKCommonsConstants.HDB_SEQUENCE_PARSER);
      applyArtefactState(hdbSequenceModel.getName(), hdbSequenceModel.getLocation(), SEQUENCE_ARTEFACT, ArtefactState.FAILED_CREATE,
          message);
    }
  }

  private String getDatabaseSpecificSQL(Connection connection, XSKDataStructureHDBSequenceModel hdbSequenceModel,
      String hdbSequenceName) {
    return SqlFactory.getNative(connection).create().sequence(hdbSequenceName)
        .start(hdbSequenceModel.getStartWith())
        .increment(hdbSequenceModel.getIncrementBy())
        .maxvalue(hdbSequenceModel.getMaxValue())
        .nomaxvalue(hdbSequenceModel.getNoMaxValue())
        .minvalue(hdbSequenceModel.getMinValue())
        .nominvalue(hdbSequenceModel.getNoMinValue())
        .cycles(hdbSequenceModel.getCycles())
        .resetBy(hdbSequenceModel.getResetBy()).build();
  }
}
