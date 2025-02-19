package com.example.primeraappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primeraappandroid.models.Actividad
import com.example.primeraappandroid.ui.theme.PrimeraAppAndroidTheme

class NotasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotasPage()
        }
    }
}

@Composable
fun NotasPage() {
    var actividad by remember { mutableStateOf<String>(value = "") }
    var nota by remember { mutableStateOf<Double>(value = 0.0) }
    var mostrarValidacion by remember { mutableStateOf<Boolean>(false) }
    var actividadobj by remember {
        mutableStateOf<Actividad>(
            Actividad(nombre = "", nota = 0.0, mostrarValidacionNota = false)
        )
    }

    PrimeraAppAndroidTheme {
        Column {
            NotasForm(
                actividadobj = actividadobj,
                actividad = actividad,
                nota = nota,
                setActividad = { actividad = it },
                setNota = { actividadobj = Actividad(nota=it, mostrarValidacionNota = true) },
                setMostrarValidacion = {mostrarValidacion=it}
            )
            if(actividadobj.mostrarValidacionNota) {
                ValidacionNota(
                    actividad = actividad,
                    nota = actividadobj.nota
                )
            }

        }
    }
}

@Composable
fun NotasForm(
    actividadobj: Actividad,
    actividad: String,
    nota: Double,
    setActividad: (value: String) -> Unit,
    setNota: (value: Double) -> Unit,
    setMostrarValidacion: (value: Boolean) -> Unit
) {
    var notaTxt by remember { mutableStateOf(actividadobj.nota.toString()) }
    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(text = stringResource(id = R.string.actividad_text))
            TextField(value = actividad,
                onValueChange = { value -> setActividad(value) }
            )
        }
        Row(
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(text = stringResource(id = R.string.nota_text))
            TextField(value = notaTxt, onValueChange = { notaTxt = it })
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.purple_200)),
            onClick = {
                setNota(notaTxt.toDouble())
                setMostrarValidacion(true)
            }) {
            Text(stringResource(id = R.string.guardar_btn))
        }
    }
}


@Composable
fun ValidacionNota(actividad: String, nota: Double) {
    if (nota >= 3) {
        Text(text = "Aprobó la actividad de: " + actividad)
    } else {
        Text(text = "Reprobó la actividad de: " + actividad)
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    heightDp = 600
)
@Composable
fun NotasPagePreview() {
    NotasPage()
}