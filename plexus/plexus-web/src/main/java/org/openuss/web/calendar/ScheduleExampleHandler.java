/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.openuss.web.calendar;

import java.io.Serializable;

import javax.faces.event.ActionEvent;

import org.apache.myfaces.custom.schedule.model.ScheduleModel;

/**
 * Handler class for the schedule example 
 * 
 * @author Jurgen Lust (latest modification by $Author$)
 * @version $Revision$
 */
public class ScheduleExampleHandler implements Serializable
{
    private static final long serialVersionUID = -8815771399735333108L;

    private ScheduleModel model;

    public ScheduleModel getModel()
    {
        return model;
    }

    public void setModel(ScheduleModel model)
    {
        this.model = model;
    }

	public void deleteSelectedEntry(ActionEvent event)
    {
        if (model == null){
            return;
        }
        model.removeSelectedEntry();
    }
}
