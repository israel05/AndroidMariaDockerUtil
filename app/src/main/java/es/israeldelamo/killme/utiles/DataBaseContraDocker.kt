package es.israeldelamo.killme.utiles

import android.content.Context
import android.database.SQLException
import android.util.Log
import es.israeldelamo.killme.R
import java.io.InputStream
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.util.Properties

class DataBaseContraDocker {


    companion object {
        fun getConnection(context: Context) {
            // Hilo para evitar operaciones bloqueantes en el hilo principal
            // Imprescindible según la API level
            val thread = Thread {
                try {
                    // Cargar el archivo de propiedades
                    val properties = loadProperties(context)
                    val dbUrl = properties.getProperty("db_url")
                    val dbUsername = properties.getProperty("db_username")
                    val dbPassword = properties.getProperty("db_password")

                    // Conectar a la base de datos
                    var conn: Connection? = null
                    try {
                        conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)

                        // Obtener y mostrar metadatos de la base de datos
                        val databaseMetaData: DatabaseMetaData = conn!!.metaData

                        //Informacióne extra, solo para verificar
                        Log.d("DATABASE", databaseMetaData.toString())
                        Log.d("DATABASE", databaseMetaData.driverName)
                        Log.d("DATABASE", databaseMetaData.driverVersion)
                        Log.d("DATABASE", databaseMetaData.databaseProductName)


                    } catch (ex: SQLException) {
                        Log.d("DATABASE", ex.toString())
                       // ex.printStackTrace()
                    } catch (ex: Exception) {
                        Log.d("DATABASE", ex.toString())
                    }
                } catch (e: Exception) {
                   Log.d("DATABASE", e.toString())
                }
            }
            thread.start()
        }

        private fun loadProperties(context: Context): Properties {
            val properties = Properties()
            try {
                // Abrir el archivo raw/datosconexio.properties
                val inputStream: InputStream = context.resources.openRawResource(R.raw.datosconexion)
                properties.load(inputStream)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return properties
        }
    }

}