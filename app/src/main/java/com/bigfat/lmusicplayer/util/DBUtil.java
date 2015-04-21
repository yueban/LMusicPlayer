package com.bigfat.lmusicplayer.util;

import android.database.sqlite.SQLiteDatabase;

import com.bigfat.lmusicplayer.common.App;
import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.DataBase;
import com.litesuits.orm.db.DataBaseConfig;
import com.litesuits.orm.db.TableManager;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;
import com.litesuits.orm.db.impl.SQLStatement;
import com.litesuits.orm.db.impl.SQLiteHelper;
import com.litesuits.orm.db.model.ColumnsValue;
import com.litesuits.orm.db.model.ConflictAlgorithm;
import com.litesuits.orm.db.model.Relation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 数据库Orm框架
 * Created by yueban on 15/4/20.
 */
public class DBUtil {
    private static final String DB_NAME = "db_audio";

    private static DataBase db;

    private DBUtil() {
    }

    private static DataBase getInstance() {
        if (db == null) {
            db = LiteOrm.newInstance(App.getContext(), DB_NAME);
        }
        return db;
    }

    public static DataBase single() {
        return getInstance().single();
    }

    public static DataBase cascade() {
        return getInstance().cascade();
    }

    public static long save(Object entity) {
        return getInstance().save(entity);
    }

    public static int save(Collection<?> collection) {
        return getInstance().save(collection);
    }

    public static long insert(Object entity) {
        return getInstance().insert(entity);
    }

    public static long insert(Object entity, ConflictAlgorithm conflictAlgorithm) {
        return getInstance().insert(entity, conflictAlgorithm);
    }

    public static int insert(Collection<?> collection) {
        return getInstance().insert(collection);
    }

    public static int insert(Collection<?> collection, ConflictAlgorithm conflictAlgorithm) {
        return getInstance().insert(collection, conflictAlgorithm);
    }

    public static int update(Object entity) {
        return getInstance().update(entity);
    }

    public static int update(Object entity, ConflictAlgorithm conflictAlgorithm) {
        return getInstance().update(entity, conflictAlgorithm);
    }

    public static int update(Object entity, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm) {
        return getInstance().update(entity, cvs, conflictAlgorithm);
    }

    public static int update(Collection<?> collection) {
        return getInstance().update(collection);
    }

    public static int update(Collection<?> collection, ConflictAlgorithm conflictAlgorithm) {
        return getInstance().update(collection, conflictAlgorithm);
    }

    public static int update(Collection<?> collection, ColumnsValue cvs, ConflictAlgorithm conflictAlgorithm) {
        return getInstance().update(collection, cvs, conflictAlgorithm);
    }

    public static int delete(Object entity) {
        return getInstance().delete(entity);
    }

    public static int delete(Class<?> claxx) {
        return getInstance().delete(claxx);
    }

    public static int deleteAll(Class<?> claxx) {
        return getInstance().deleteAll(claxx);
    }

    public static int delete(Class<?> claxx, long start, long end, String orderAscColu) {
        return getInstance().delete(claxx, start, end, orderAscColu);
    }

    public static int delete(Collection<?> collection) {
        return getInstance().delete(collection);
    }

    public static int delete(Class<?> claxx, WhereBuilder where) {
        return getInstance().delete(claxx, where);
    }

    public static <T> ArrayList<T> query(QueryBuilder qb) {
        return getInstance().query(qb);
    }

    public static <T> T queryById(long id, Class<T> clazz) {
        return getInstance().queryById(id, clazz);
    }

    public static <T> T queryById(String id, Class<T> clazz) {
        return getInstance().queryById(id, clazz);
    }

    public static long queryCount(Class<?> claxx) {
        return getInstance().queryCount(claxx);
    }

    public static long queryCount(QueryBuilder qb) {
        return getInstance().queryCount(qb);
    }

    public static <T> ArrayList<T> queryAll(Class<T> claxx) {
        return getInstance().queryAll(claxx);
    }

    public static SQLStatement createSQLStatement(String sql, Object[] bindArgs) {
        return getInstance().createSQLStatement(sql, bindArgs);
    }

    public static boolean execute(SQLiteDatabase db, SQLStatement statement) {
        return getInstance().execute(db, statement);
    }

    public static boolean dropTable(Object entity) {
        return getInstance().dropTable(entity);
    }

    public static boolean dropTable(String tableName) {
        return getInstance().dropTable(tableName);
    }

    public static ArrayList<Relation> queryRelation(Class class1, Class class2, List<String> key1List, List<String> key2List) {
        return getInstance().queryRelation(class1, class2, key1List, key2List);
    }

    public static <E, T> boolean mapping(Collection<E> col1, Collection<T> col2) {
        return getInstance().mapping(col1, col2);
    }

    public static SQLiteDatabase getReadableDatabase() {
        return getInstance().getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDatabase() {
        return getInstance().getWritableDatabase();
    }

    public static TableManager getTableManager() {
        return getInstance().getTableManager();
    }

    public static SQLiteHelper getSQLiteHelper() {
        return getInstance().getSQLiteHelper();
    }

    public static DataBaseConfig getDataBaseConfig() {
        return getInstance().getDataBaseConfig();
    }

    public static void close() {
        getInstance().close();
    }
}
