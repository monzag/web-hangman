package com.codecool.webhangman.service.permissionsmanagementservice;

import java.lang.reflect.Field;

class ClassContainer {
    private Class<?> _class;
    private Field[] fields;

    private ClassContainer(Class<?> _class, Field[] fields) {
        this._class = _class;
        this.fields = fields;
    }

    public static ClassContainer create(Class<?> _class, Field[] fields) {
        return new ClassContainer(_class, fields);
    }

    public Class<?> get_class( ) {
        return _class;
    }

    public Field[] getFields() {
        return fields;
    }
}
