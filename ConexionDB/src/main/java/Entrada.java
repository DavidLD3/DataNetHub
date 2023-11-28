import database.GestionDB;
import database.SchemeDB;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class Entrada {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String jsonUrl = "https://dummyjson.com/products";
        String jsonContent;
        try {
            jsonContent = getContentFromUrl(jsonUrl);
        } catch (IOException e) {
            throw new RuntimeException("Error al obtener el JSON del URL", e);
        }

        JSONObject json = new JSONObject(new JSONTokener(jsonContent));
        JSONArray productos = json.getJSONArray("products");


        int opcion;
        do {

            System.out.println("Presiona un número para ejecutar una acción:");
            System.out.println("1 - Mostrar Empleados");
            System.out.println("2 - Mostrar Productos");
            System.out.println("3 - Mostrar Pedidos");
            System.out.println("4 - Mostrar Productos con Precio Inferior a 600");
            System.out.println("5 - Mostrar Productos con Precio Superior a 1000");
            System.out.println("6 - Insertar Empleados");
            System.out.println("7 - Insertar Productos");
            System.out.println("8 - Insertar Pedidos");
            System.out.println("9 - Insertar Productos +1000");
            System.out.println("0 - Salir");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    mostrarEmpleados();
                    break;
                case 2:
                    mostrarProductos();
                    break;
                case 3:
                    mostrarPedidos();
                    break;
                case 4:
                    mostrarProductosConPrecioInferiorA600();
                    break;
                case 5:
                    mostrarProductosFavSuperioresA1000() ;
                    break;
                case 6:
                    insertarEmpleados();
                    break;
                case 7:
                    insertarProductos(productos);
                    break;
                case 8:
                    insertarPedidos();
                    break;
                case 9:
                    insertarProductosFav();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicacion");
                    break;
                default:
                    System.out.println("Opción no valida");
            }

        } while (opcion != 0);

        scanner.close();

    }

    private static String getContentFromUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new IOException("Error al leer el contenido desde la URL", e);
        }

        return content.toString();
    }

    private static void insertarProductos(JSONArray productos) {
        Connection connection = GestionDB.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                            SchemeDB.TAB_NAME_Pro, SchemeDB.COL_NAME, SchemeDB.COL_DESCRIPTION,
                            SchemeDB.COL_QUANTITY, SchemeDB.COL_PRICE));

            for (int i = 0; i < productos.length(); i++) {
                JSONObject producto = productos.getJSONObject(i);

                preparedStatement.setString(1, producto.getString("title"));
                preparedStatement.setString(2, producto.getString("description"));
                preparedStatement.setInt(3, producto.getInt("stock"));
                preparedStatement.setDouble(4, producto.getDouble("price"));

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar productos", e);
        }
    }

    private static void insertarEmpleados() {
        Connection connection = GestionDB.getConnection();

        try {
            String insertEmpleadosQuery = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                    SchemeDB.TAB_NAME_Enp, SchemeDB.COL_NAME, SchemeDB.COL_LASTNAME, SchemeDB.COL_MAIL);

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertEmpleadosQuery)) {
                preparedStatement.setString(1, "Juan");
                preparedStatement.setString(2, "Ramon");
                preparedStatement.setString(3, "juan@gmail.com");

                preparedStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertEmpleadosQuery)) {
                preparedStatement.setString(1, "María");
                preparedStatement.setString(2, "Gomez");
                preparedStatement.setString(3, "maria@gmail.com");

                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar empleados", e);
        }
    }

    private static void insertarPedidos() {
        Connection connection = GestionDB.getConnection();

        try {
            String insertPedidoQuery = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                    SchemeDB.TAB_NAME_Ped,SchemeDB.COL_ID_PRODUCT, SchemeDB.COL_DESCRIPCION, SchemeDB.COL_TOTAL);

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertPedidoQuery)) {
                preparedStatement.setInt(1, 6);  // ID del producto
                preparedStatement.setString(2,"OPPO F19 is officially announced on April 2021");
                preparedStatement.setDouble(3, 280);  // Total del pedido

                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertPedidoQuery)) {
                preparedStatement.setInt(1, 5);  // ID del producto
                preparedStatement.setString(2,"Samsung's new variant which goes beyond Galaxy to the Universe");
                preparedStatement.setDouble(3, 1249);  // Total del pedido

                preparedStatement.executeUpdate();
            }
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertPedidoQuery)) {
                preparedStatement.setInt(1, 8);  // ID del producto
                preparedStatement.setString(2,"MacBook Pro 2021 with mini-LED display may launch between September, November");
                preparedStatement.setDouble(3, 1749);  // Total del pedido

                preparedStatement.executeUpdate();
            }

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertPedidoQuery)) {
                preparedStatement.setInt(1, 11);  // ID del producto
                preparedStatement.setString(2,"Infinix Inbook X1 Ci3 10th 8GB 256GB 14 Win10 Grey – 1 Year Warranty");
                preparedStatement.setDouble(3, 1099);  // Total del pedido

                preparedStatement.executeUpdate();
            }


            // Puedes seguir agregando más pedidos según tus necesidades.

            System.out.println("Pedidos manuales insertados correctamente.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar pedidos manualmente", e);
        }
    }

    private static void mostrarEmpleados() {
        Connection connection = GestionDB.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", SchemeDB.TAB_NAME_Enp));

            while (resultSet.next()) {
                String nombre = resultSet.getString(SchemeDB.COL_NAME);
                String apellido = resultSet.getString(SchemeDB.COL_LASTNAME);
                String correo = resultSet.getString(SchemeDB.COL_MAIL);

                System.out.println(String.format("Empleado: %s %s, Correo: %s", nombre, apellido, correo));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar empleados", e);
        }
    }

    private static void mostrarProductos() {
        Connection connection = GestionDB.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", SchemeDB.TAB_NAME_Pro));

            while (resultSet.next()) {
                String nombre = resultSet.getString(SchemeDB.COL_NAME);
                String descripcion = resultSet.getString(SchemeDB.COL_DESCRIPTION);
                int cantidad = resultSet.getInt(SchemeDB.COL_QUANTITY);
                double precio = resultSet.getDouble(SchemeDB.COL_PRICE);

                System.out.println(String.format("Producto: %s, Descripcion: %s, Cantidad: %d, Precio: %.2f",
                        nombre, descripcion, cantidad, precio));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar productos", e);
        }
    }
    private static void mostrarPedidos() {
        Connection connection = GestionDB.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM %s", SchemeDB.TAB_NAME_Ped));

            while (resultSet.next()) {
                int idProducto = resultSet.getInt(SchemeDB.COL_ID_PRODUCT);
                String descripcion = resultSet.getString(SchemeDB.COL_DESCRIPCION);
                double total = resultSet.getDouble(SchemeDB.COL_TOTAL);

                System.out.println(String.format("Pedido: Producto ID %d, Descripcion: %s, Total: %.2f",
                        idProducto, descripcion, total));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar pedidos", e);
        }
    }
    private static void mostrarProductosConPrecioInferiorA600() {
        Connection connection = GestionDB.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM %s WHERE %s < 600", SchemeDB.TAB_NAME_Pro, SchemeDB.COL_PRICE));

            while (resultSet.next()) {
                String nombre = resultSet.getString(SchemeDB.COL_NAME);
                double precio = resultSet.getDouble(SchemeDB.COL_PRICE);

                System.out.println(String.format("Producto: %s, Precio: %.2f", nombre, precio));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar productos con precio inferior a 600€", e);
        }
    }
    private static void mostrarProductosFavSuperioresA1000() {
        Connection connection = GestionDB.getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    String.format("SELECT * FROM %s WHERE %s > 1000", SchemeDB.TAB_NAME_Pro, SchemeDB.COL_PRICE));

            while (resultSet.next()) {
                String nombre = resultSet.getString(SchemeDB.COL_NAME);
                double precio = resultSet.getDouble(SchemeDB.COL_PRICE);

                System.out.println(String.format("Producto: %s, Precio: %.2f", nombre, precio));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar productos con precio inferior a 600€", e);
        }
    }
    private static void insertarProductosFav() {
        Connection connection = GestionDB.getConnection();

        try {
            String insertProductosFavQuery = String.format("INSERT INTO %s (%s) SELECT %s FROM %s WHERE %s > 1000",
                    "Productos_Fav", "id_producto", "id", "Productos", "precio");

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertProductosFavQuery)) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar productos con precio superior a 1000€", e);
        }
    }

}
