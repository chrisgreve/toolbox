/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2016-2021 Gerrit Grunwald.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.hansolo.toolbox.properties;


import eu.hansolo.toolbox.evt.type.PropertyChangeEvt;


public class IntegerProperty extends ReadOnlyIntegerProperty {
    protected ReadOnlyIntegerProperty propertyBoundTo;
    protected boolean                 bound;


    // ******************** Constructors **************************************
    public IntegerProperty() {
        this(null, null, 0);
    }
    public IntegerProperty(final int value) {
        this(null, null, value);
    }
    public IntegerProperty(final String name, final int value) {
        this(null, name, value);
    }
    public IntegerProperty(final Object bean, final String name, final int value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.propertyBoundTo  = null;
        this.bound            = false;
        this.bidirectional    = false;
    }


    // ******************** Methods *******************************************
    public void setValue(final Integer value) {
        if (bound && !bidirectional) { throw new IllegalArgumentException("A bound value cannot be set."); }
        setValue(value, null);
    }
    public void set(final int value) { setValue(value); }
    protected void setValue(final Integer value, final IntegerProperty property) {
        willChange(this.value, value);
        final Integer oldValue = this.value;
        this.value = value;
        if (null == property && null != this.propertyToUpdate) {
            this.propertyToUpdate.setValue(value, this);
        }
        fireEvent(new PropertyChangeEvt(this, PropertyChangeEvt.CHANGED, oldValue, this.value));
        didChange(oldValue, this.value);
        invalidated();
    }

    public void unset() { setValue(getInitialValue()); }

    public void setInitialValue(final Integer initialValue) { this.initialValue = initialValue; }

    public void bind(final ReadOnlyIntegerProperty property) {
        this.propertyBoundTo = property;
        this.value           = this.propertyBoundTo.getValue();
        propertyBoundTo.setPropertyToUpdate(this);
        propertyToUpdate = null;
        this.bound       = true;
    }
    public boolean isBound() { return this.bound; }

    public void bindBidirectional(final IntegerProperty property) {
        setPropertyToUpdate(property, true);
        property.setPropertyToUpdate(this, true);
        this.propertyBoundTo = property;
        this.bound           = true;
    }
    public boolean isBoundBidirectional() { return this.bidirectional; }

    public void unbind() {
        if (null != this.propertyToUpdate) {
            this.propertyToUpdate.unsetPropertyToUpdate();
            this.propertyToUpdate.unbind();
            this.propertyToUpdate = null;
        }
        if (null != this.propertyBoundTo) {
            this.propertyBoundTo.unsetPropertyToUpdate();
            this.propertyBoundTo = null;
        }
        this.bound         = false;
        this.bidirectional = false;
    }

    protected void setPropertyToUpdate(final IntegerProperty property, final boolean bidirectional) {
        this.propertyToUpdate = property;
        if (null == property) {
            this.bidirectional = false;
        } else {
            this.value = property.getValue();
            this.bidirectional = bidirectional;
        }
    }
}