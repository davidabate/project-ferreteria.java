import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Clase principal que representa un producto genérico en la ferretería
class Producto {
    private String codigo;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;

    public Producto(String codigo, String nombre, String descripcion, double precio, int stock) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters y setters
    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public double getPrecio() { return precio; }
    public int getStock() { return stock; }
    
    public void setStock(int stock) { this.stock = stock; }
    public void setPrecio(double precio) { this.precio = precio; }

    // Método para reducir stock cuando se vende
    public boolean vender(int cantidad) {
        if (cantidad <= stock) {
            stock -= cantidad;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Código: " + codigo + ", Nombre: " + nombre + ", Precio: $" + precio + ", Stock: " + stock;
    }
}

// Subclase para herramientas específicas
class Herramienta extends Producto {
    private String tipo;
    private String marca;
    private boolean electrica;

    public Herramienta(String codigo, String nombre, String descripcion, double precio, int stock,
                      String tipo, String marca, boolean electrica) {
        super(codigo, nombre, descripcion, precio, stock);
        this.tipo = tipo;
        this.marca = marca;
        this.electrica = electrica;
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: " + tipo + ", Marca: " + marca + 
               ", Eléctrica: " + (electrica ? "Sí" : "No");
    }

    // Getters específicos
    public String getTipo() { return tipo; }
    public String getMarca() { return marca; }
    public boolean isElectrica() { return electrica; }
}

// Subclase para materiales de construcción
class Material extends Producto {
    private String unidadMedida;
    private String categoria;

    public Material(String codigo, String nombre, String descripcion, double precio, int stock,
                   String unidadMedida, String categoria) {
        super(codigo, nombre, descripcion, precio, stock);
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return super.toString() + ", Unidad: " + unidadMedida + ", Categoría: " + categoria;
    }

    // Getters específicos
    public String getUnidadMedida() { return unidadMedida; }
    public String getCategoria() { return categoria; }
}

// Clase para representar clientes
class Cliente {
    private String id;
    private String nombre;
    private String direccion;
    private String telefono;
    private boolean mayorista;

    public Cliente(String id, String nombre, String direccion, String telefono, boolean mayorista) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.mayorista = mayorista;
    }

    // Getters
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getTelefono() { return telefono; }
    public boolean isMayorista() { return mayorista; }

    @Override
    public String toString() {
        return "ID: " + id + ", Nombre: " + nombre + ", Tel: " + telefono + ", " +
               (mayorista ? "Mayorista" : "Minorista");
    }
}

// Clase para gestionar transacciones de venta
class Venta {
    private String codigoVenta;
    private Cliente cliente;
    private Map<Producto, Integer> productos;
    private double total;
    private String fecha;

    public Venta(String codigoVenta, Cliente cliente, String fecha) {
        this.codigoVenta = codigoVenta;
        this.cliente = cliente;
        this.fecha = fecha;
        this.productos = new HashMap<>();
        this.total = 0;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        if (producto.vender(cantidad)) {
            productos.put(producto, cantidad);
            double descuento = cliente.isMayorista() ? 0.15 : 0.0;
            total += (producto.getPrecio() * cantidad) * (1 - descuento);
        }
    }

    public void mostrarDetalle() {
        System.out.println("=== Factura ===");
        System.out.println("Venta #" + codigoVenta);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Fecha: " + fecha);
        System.out.println("\nProductos:");

        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto p = entry.getKey();
            int cantidad = entry.getValue();
            System.out.println(p.getNombre() + " x" + cantidad + " - $" + (p.getPrecio() * cantidad));
        }

        System.out.println("\nTOTAL: $" + total);
    }
}

// Clase principal que gestiona el inventario de la ferretería
class Inventario {
    private List<Producto> productos;
    private List<Cliente> clientes;
    private List<Venta> ventas;

    public Inventario() {
        productos = new ArrayList<>();
        clientes = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void agregarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void registrarVenta(Venta venta) {
        ventas.add(venta);
    }

    public Producto buscarProducto(String codigo) {
        for (Producto p : productos) {
            if (p.getCodigo().equals(codigo)) {
                return p;
            }
        }
        return null;
    }

    public Cliente buscarCliente(String id) {
        for (Cliente c : clientes) {
            if (c.getId().equals(id)) {
                return c;
            }
        }
        return null;
    }

    public void mostrarInventario() {
        System.out.println("\n=== INVENTARIO ===");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public void mostrarClientes() {
        System.out.println("\n=== CLIENTES ===");
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    public void mostrarVentas() {
        System.out.println("\n=== HISTORIAL DE VENTAS ===");
        for (Venta v : ventas) {
            v.mostrarDetalle();
            System.out.println();
        }
    }
}

// Clase principal para probar el sistema
public class Ferreteria {
    public static void main(String[] args) {
        // Crear inventario
        Inventario inventario = new Inventario();

        // Agregar algunos productos
        inventario.agregarProducto(new Herramienta(
            "H01", "Taladro", "Taladro percutor 650W", 350.99, 10,
            "Eléctrica", "Black & Decker", true
        ));
        
        inventario.agregarProducto(new Herramienta(
            "H02", "Martillo", "Martillo de carpintero 16oz", 25.50, 30,
            "Manual", "Stanley", false
        ));
        
        inventario.agregarProducto(new Material(
            "M01", "Cemento", "Cemento gris 50kg", 120.00, 50,
            "Bolsa", "Construcción"
        ));
        
        inventario.agregarProducto(new Material(
            "M02", "Clavo", "Clavo acerado 2.5\"", 5.75, 200,
            "Kilogramo", "Ferretería"
        ));

        // Agregar algunos clientes
        inventario.agregarCliente(new Cliente(
            "C001", "Construcciones SA", "Av. Principal 123", "5551234", true
        ));
        
        inventario.agregarCliente(new Cliente(
            "C002", "Juan Pérez", "Calle Secundaria 456", "5555678", false
        ));

        // Mostrar estado inicial
        inventario.mostrarInventario();
        inventario.mostrarClientes();

        // Realizar una venta
        Cliente clienteVenta = inventario.buscarCliente("C002");
        Venta venta = new Venta("V001", clienteVenta, "2023-10-15");
        venta.agregarProducto(inventario.buscarProducto("H02"), 2);
        venta.agregarProducto(inventario.buscarProducto("M02"), 5);
        inventario.registrarVenta(venta);

        // Mostrar detalle de la venta
        venta.mostrarDetalle();

        // Mostrar estado final
        inventario.mostrarInventario();
        inventario.mostrarVentas();
    }
}

