/**
 * generated by Xtext 2.18.0
 */
package com.sap.xsk.models.hdbtable.tests;

import com.google.inject.Inject;
import com.sap.xsk.models.hdbtable.hdbTable.HdbTableModel;
import com.sap.xsk.models.hdbtable.tests.HdbTableInjectorProvider;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.XtextRunner;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(XtextRunner.class)
@InjectWith(HdbTableInjectorProvider.class)
@SuppressWarnings("all")
public class HdbTableParsingTest {
  @Inject
  private ParseHelper<HdbTableModel> parseHelper;
  
  @Test
  public void loadModel() {
    try {
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("table.schemaName = \"SPORTS\";");
      _builder.newLine();
      _builder.append("table.tableType = COLUMNSTORE;");
      _builder.newLine();
      _builder.append("table.description = \"Team players\";");
      _builder.newLine();
      _builder.append("table.columns = [");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{ name = \"MATCH_ID\";\tsqlType = NVARCHAR;\t\tlength = 32;\tcomment=\"test\"; \tnullable = false; },");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{ name = \"TEAM_ID\";\t\tsqlType = NVARCHAR;\t\tlength = 32;\t\t\t\t\t\tnullable = false; },");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{ name = \"PERSON_ID\";\tsqlType = NVARCHAR;\t\tlength = 32;\t\t\t\t\t\tnullable = false; },");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{ name = \"CHANGED_BY\";\tsqlType = NVARCHAR; \tlength = 256;\t\t\t\t\t\tnullable = true; },");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("{ name = \"CHANGED_AT\";\tsqlType = TIMESTAMP; \t\t\t\t\t\t\t\t\t\tnullable = true; }");
      _builder.newLine();
      _builder.append("];");
      _builder.newLine();
      _builder.append("table.primaryKey.pkcolumns = [\"MATCH_ID\", \"TEAM_ID\", \"PERSON_ID\"];");
      _builder.newLine();
      final HdbTableModel result = this.parseHelper.parse(_builder);
      Assert.assertNotNull(result);
      final EList<Resource.Diagnostic> errors = result.eResource().getErrors();
      StringConcatenation _builder_1 = new StringConcatenation();
      _builder_1.append("Unexpected errors: ");
      String _join = IterableExtensions.join(errors, ", ");
      _builder_1.append(_join);
      Assert.assertTrue(_builder_1.toString(), errors.isEmpty());
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
}
