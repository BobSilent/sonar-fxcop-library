/*
 * SonarQube FxCop Library
 * Copyright (C) 2014 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.fxcop;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.google.common.io.Files;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FxCopRulesetWriter {

  public void write(List<String> ruleConfigKeys, File file) {
    StringBuilder sb = new StringBuilder();

    appendLine(sb, "<?xml version=\"1.0\" encoding=\"utf-8\"?>");
    appendLine(sb, "<RuleSet Name=\"SonarQube\" Description=\"Rule set generated by SonarQube\" ToolsVersion=\"12.0\">");

    appendLine(sb, "  <Rules AnalyzerId=\"Microsoft.Analyzers.ManagedCodeAnalysis\" RuleNamespace=\"Microsoft.Rules.Managed\">");

    for (String ruleConfigKey : ruleConfigKeys) {
      appendLine(sb, "    <Rule Id=\"" + ruleConfigKey + "\" Action=\"Error\" />");
    }

    appendLine(sb, "  </Rules>");

    appendLine(sb, "</RuleSet>");

    try {
      Files.write(sb.toString().getBytes(Charsets.UTF_8), file);
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static void appendLine(StringBuilder sb, String s) {
    sb.append(s);
    sb.append(IOUtils.LINE_SEPARATOR);
  }

}
