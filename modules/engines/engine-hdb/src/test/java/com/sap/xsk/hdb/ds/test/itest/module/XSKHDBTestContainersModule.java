/*
 * Copyright (c) 2019-2021 SAP SE or an SAP affiliate company and XSK contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Apache License, v2.0
 * which accompanies this distribution, and is available at
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * SPDX-FileCopyrightText: 2019-2021 SAP SE or an SAP affiliate company and XSK contributors
 * SPDX-License-Identifier: Apache-2.0
 */
package com.sap.xsk.hdb.ds.test.itest.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.sap.xsk.hdb.ds.module.XSKHDBModule;
import com.sap.xsk.hdb.ds.test.itest.hdbsequence.XSKHDBSequenceParserPostgreSQLITCase;
import org.apache.commons.dbcp2.BasicDataSource;
import org.eclipse.dirigible.repository.api.RepositoryPath;
import org.eclipse.dirigible.repository.fs.FileSystemRepository;
import org.eclipse.dirigible.repository.local.LocalRepository;
import org.eclipse.dirigible.repository.local.LocalResource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import javax.sql.DataSource;
import java.io.IOException;

public class XSKHDBTestContainersModule extends AbstractModule {

  private JdbcDatabaseContainer jdbcContainer;

  public XSKHDBTestContainersModule(JdbcDatabaseContainer jdbcContainer) {
    this.jdbcContainer = jdbcContainer;
  }

  @Override
  protected void configure() {
    install(new XSKHDBModule());
  }

  @Provides
  public DataSource getDataSource() {
    BasicDataSource basicDataSource = new BasicDataSource();
    basicDataSource.setDriverClassName(this.jdbcContainer.getDriverClassName());
    basicDataSource.setUrl(this.jdbcContainer.getJdbcUrl());
    basicDataSource.setUsername(this.jdbcContainer.getUsername());
    basicDataSource.setPassword(this.jdbcContainer.getPassword());
    basicDataSource.setDefaultAutoCommit(true);
    basicDataSource.setAccessToUnderlyingConnectionAllowed(true);
    return basicDataSource;

  }

  public static LocalResource getResources(String rootFolder, String repoPath, String relativeResourcePath) throws IOException {
    FileSystemRepository fileRepo = new LocalRepository(rootFolder);
    RepositoryPath path = new RepositoryPath(repoPath);
    byte[] content = XSKHDBSequenceParserPostgreSQLITCase.class
        .getResourceAsStream(relativeResourcePath).readAllBytes();

    LocalResource resource = new LocalResource(fileRepo, path);
    resource.setContent(content);
    return resource;
  }
}
