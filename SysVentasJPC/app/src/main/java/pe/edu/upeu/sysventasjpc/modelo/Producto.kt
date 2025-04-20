package pe.edu.upeu.sysventasjpc.modelo

data class ProductoDto (
    val idProducto: Long,
    val nombre: String,
    val pu: Double,
    val puOld: Double,
    val utilidad: Double,
    val stock: Double,
    val stockOld: Double,
    val categoria: Long,
    val marca: Long,
    val unidadMedida: Long
)

data class ProductoRespon (
    val idProducto: Long,
    val nombre: String,
    val pu: Double,
    val puOld: Double,
    val utilidad: Double,
    val stock: Double,
    val stockOld: Double,
    val categoria: Categoria,
    val marca: Marca,
    val unidadMedida: UnidadMedida
)