# backend/app.py

from flask import Flask
from flask_cors import CORS
from app.routes.auth_routes import auth_bp

app = Flask(__name__)
CORS(app)

# Registrar as rotas
app.register_blueprint(auth_bp, url_prefix="/auth")

if __name__ == "__main__":
    app.run(debug=True)
