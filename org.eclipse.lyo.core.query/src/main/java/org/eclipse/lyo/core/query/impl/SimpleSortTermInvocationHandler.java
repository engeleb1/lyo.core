/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution. 
 *
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *
 *    Steve Pitschke - initial API and implementation
 *******************************************************************************/
package org.eclipse.lyo.core.query.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.antlr.runtime.tree.CommonTree;
import org.eclipse.lyo.core.query.SimpleSortTerm;
import org.eclipse.lyo.core.query.SortTerm.Type;

/**
 * @author pitschke
 *
 */
public class SimpleSortTermInvocationHandler extends SortTermInvocationHandler
{
    public
    SimpleSortTermInvocationHandler(
        CommonTree tree,
        Map<String, String> prefixMap
    )
    {
        super(Type.SIMPLE, tree, prefixMap);
     }

    /**
     * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
     */
    @Override
    public Object
    invoke(
        Object proxy,
        Method method,
        Object[] args
    ) throws Throwable
    {
        String methodName = method.getName();
        boolean isAscending = methodName.equals("ascending");
        
        if (! isAscending &&
            ! methodName.equals("toString")) {
            return super.invoke(proxy, method, args);
        }
        
        if (ascending == null) {
            ascending = tree.getChild(1).getText().equals("+");
        }
        
        if (isAscending) {
            return ascending;
        }
        
        return (ascending ? '+' : '-') +
            ((SimpleSortTerm)proxy).identifier().toString();
    }

    private Boolean ascending = null;
}
