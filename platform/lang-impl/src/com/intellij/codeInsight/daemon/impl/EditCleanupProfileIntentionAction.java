/*
 * Copyright 2000-2014 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.codeInsight.daemon.impl;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInspection.InspectionProfile;
import com.intellij.codeInspection.ex.InspectionToolWrapper;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.profile.codeInspection.InspectionProfileManager;
import com.intellij.profile.codeInspection.InspectionProjectProfileManager;
import com.intellij.profile.codeInspection.ui.IDEInspectionToolsConfigurable;
import com.intellij.profile.codeInspection.ui.ProjectInspectionToolsConfigurable;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
* Created by anna on 5/13/2014.
*/
class EditCleanupProfileIntentionAction implements IntentionAction {
  static final EditCleanupProfileIntentionAction INSTANCE = new EditCleanupProfileIntentionAction();
  private EditCleanupProfileIntentionAction() {}

  @Override
  @NotNull
  public String getText() {
    return getFamilyName();
  }

  @Override
  @NotNull
  public String getFamilyName() {
    return "Edit cleanup profile settings";
  }

  @Override
  public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
    return true;
  }

  @Override
  public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
    final InspectionProjectProfileManager profileManager = InspectionProjectProfileManager.getInstance(project);
    final ProjectInspectionToolsConfigurable configurable =
      new ProjectInspectionToolsConfigurable(InspectionProfileManager.getInstance(), profileManager) {
        @Override
        protected boolean acceptTool(InspectionToolWrapper entry) {
          return super.acceptTool(entry) && entry.isCleanupTool();
        }
      };
    ShowSettingsUtil.getInstance().editConfigurable(project, configurable);
  }

  @Override
  public boolean startInWriteAction() {
    return false;
  }
}
