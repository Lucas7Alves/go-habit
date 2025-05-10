from flask import Flask
from flask_cors import CORS
from app.routes.auth_routes import auth_bp
from app.routes.grafico_routes import grafico_bp  # Importando a rota do gráfico

app = Flask(__name__)
CORS(app)

# Registrar as rotas
app.register_blueprint(auth_bp, url_prefix="/auth")
app.register_blueprint(grafico_bp)  # Registrando a rota para o gráfico sem prefixo

if __name__ == "__main__":
    app.run(debug=True)
