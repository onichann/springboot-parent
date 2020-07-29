package com.wt.springboot;

import com.healthmarketscience.jackcess.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wutong
 * @date 2020/7/24 15:21
 * introduction
 */
public class MdbHelper {

    private Database database;

    public MdbHelper(File mdbFile) throws IOException {
        this.database=DatabaseBuilder.open(mdbFile);
    }

    public Set<String> getTableNames() throws IOException {
        return database.getTableNames();
    }

    public List<String> queryFields(String tableName) throws IOException {
        Table table  = database.getTable(tableName);
        return table.getColumns().stream().map(Column::getName).collect(Collectors.toList());
    }

    public Table getTable(String tableName) throws IOException {
        return database.getTable(tableName);
    }

    public List<Map<String, Object>> getTableData(String tableName) throws IOException {
        Table table = getTable(tableName);
        return getValueFromColumn(table.getColumns(), table.iterator());

    }

    public List<Map<String, Object>> getValueFromColumn(List<? extends Column> columns, Iterator<Row> rowIterator) {
        List<Map<String, Object>> results = new ArrayList<>();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Map<String, Object> map = new HashMap<>();
            for (Column column : columns) {
                String name = column.getName();
                switch (column.getType()) {
                    case INT:
                        if(Objects.equals(Short.class.getName(),row.get(name).getClass().getName())){
                            map.put(name, row.getShort(name));
                        }else{
                            map.put(name, row.getInt(name));
                        }
                        break;
                    case FLOAT:
                        map.put(name, row.getFloat(name));
                        break;
                    case LONG:
                        map.put(name, row.get(name));
                        break;
                    case DOUBLE:
                        map.put(name, row.getDouble(name));
                        break;
                    case MONEY:
                        map.put(name, row.getBigDecimal(name));
                        break;
                    case BOOLEAN:
                        map.put(name, row.getBoolean(name));
                        break;
                    case TEXT:
                        map.put(name, row.getString(name));
                        break;
                    case SHORT_DATE_TIME:
                        map.put(name, row.getLocalDateTime(name));
                        break;
                    default:
                        map.put(name, row.get(name));
                }
            }
            results.add(map);
        }
        return results;
    }
}
