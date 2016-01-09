package dbfit.util;

import dbfit.api.PreparedDbCommand;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class DbAutoGeneratedKeyAccessor extends DbParameterAccessor {

    public DbAutoGeneratedKeyAccessor(DbParameterAccessor c) {
        super(c.getName(), Direction.OUTPUT, c.getSqlType(), c.getJavaType(), c.getPosition(), c.getDbfitToJdbcTransformerFactory());
    }

    @Override
    public void bindTo(PreparedDbCommand statement, int ind) throws SQLException {
        this.statement = statement;
    }

    @Override
    public void set(Object value) throws Exception {
        throw new UnsupportedOperationException("Trying to set value of output parameter " + getName());
    }

    @Override
    public Object get() throws IllegalAccessException, InvocationTargetException {
        try {
            return statement.getGeneratedKey(getJavaType());
        } catch (SQLException e) {
            throw new InvocationTargetException(e);
        }
    }
}
