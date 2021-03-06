/*
 * Copyright 2000-2010 JetBrains s.r.o.
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
package com.intellij.lang;

import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.ILightStubFileElementType;
import com.intellij.util.CharTable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Abstract syntax tree built up from light nodes.
 */
public abstract class LighterAST {
  private final CharTable myCharTable;

  public LighterAST(final CharTable charTable) {
    myCharTable = charTable;
  }

  @NotNull
  public CharTable getCharTable() {
    return myCharTable;
  }

  @NotNull
  public abstract LighterASTNode getRoot();

  @Nullable
  public abstract LighterASTNode getParent(@NotNull final LighterASTNode node);

  @NotNull
  public abstract List<LighterASTNode> getChildren(@NotNull final LighterASTNode parent);

  public static @Nullable LighterAST getLighterASTFromFileAST(@NotNull FileASTNode node, @NotNull Language language) {
    final IFileElementType contentType = LanguageParserDefinitions.INSTANCE.forLanguage(language).getFileNodeType();
    assert contentType instanceof ILightStubFileElementType;

    final LighterAST tree;
    if (!node.isParsed()) {
      final ILightStubFileElementType<?> type = (ILightStubFileElementType)contentType;
      tree = new FCTSBackedLighterAST(node.getCharTable(), type.parseContentsLight(node));
    }
    else {
      tree = new TreeBackedLighterAST(node);
    }
    return tree;
  }
}