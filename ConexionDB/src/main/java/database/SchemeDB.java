package database;

public interface SchemeDB {

    String DB_NAME = "almacen";

    String HOST = "jdbc:mysql://127.0.0.1:3306";

    String TAB_NAME_Enp = "empleados";

    String TAB_NAME_Ped = "pedidos";

    String TAB_NAME_Pro = "productos";

    String TAB_NAME_Pro_Fav = "productos_fav";

    String COL_ID_PRODUCT = "id_producto";

    String COL_NAME = "nombre";

    String COL_LASTNAME = "apellidos";

    String COL_MAIL = "correo";

    String COL_PRICE = "precio";

    String COL_DESCRIPTION = "descripcion";

    String COL_TOTAL = "precio_total";

    String COL_QUANTITY = "cantidad";

    String COL_DESCRIPCION = "descripción";
}
