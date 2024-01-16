package pl.edu.agh.utp.activity;

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import pl.edu.agh.utp.R

class GraphActivity : AppCompatActivity() {

@SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_graph)

            val webView = findViewById<WebView>(R.id.graphWebView)

        // Włącz obsługę JavaScript w WebView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Ładuj kod Kotlin/JS do WebView
        val html = """
            <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Graph Visualization</title>
    <script type="text/javascript" src="https://unpkg.com/vis-network/standalone/umd/vis-network.min.js"></script>
</head>
<body>
    <div id="graph-container" style="height: 400px;"></div>

    <script type="text/javascript">
        // Przyjmuje macierz sąsiedztwa (0 - brak krawędzi, 1 - istnieje krawędź)
        const adjacencyMatrix = [
            [0, 1, 1, 0],
            [1, 0, 1, 1],
            [1, 1, 0, 1],
            [0, 1, 1, 0]
        ];

        // Tworzy tablicę wierzchołków i krawędzi na podstawie macierzy sąsiedztwa
       const nodes = new vis.DataSet([...Array(adjacencyMatrix.length)].map((_, i) => ({ id: i, label: "Node " + (i + 1).toString() })));
        const edges = new vis.DataSet([]);

        for (let i = 0; i < adjacencyMatrix.length; i++) {
            for (let j = i + 1; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j] === 1) {
                    edges.add({ from: i, to: j });
                }
            }
        }

        // Konfiguracja opcji dla grafu
        const options = {
            layout: {
                hierarchical: false // Możesz dostosować układ w zależności od potrzeb
            },
            edges: {
                color: "#000000"
            },
            nodes: {
                color: {
                    background: "#ffffff"
                }
            }
        };

        // Konfiguracja danych dla grafu
        const data = {
            nodes: nodes,
            edges: edges
        };

        // Inicjalizuje obiekt network
        const container = document.getElementById("graph-container");
        const network = new vis.Network(container, data, options);
    </script>
</body>
</html>

        """


        webView.loadDataWithBaseURL("", html, "text/html", "UTF-8", "")


        }
private fun getJsCodeBase64(): String {
        val jsCode = """
            function getHelloMessage() {
                return "nigger on the trigger";
            }
        """.trimIndent()

        return android.util.Base64.encodeToString(jsCode.toByteArray(), android.util.Base64.NO_WRAP)
        }
        }