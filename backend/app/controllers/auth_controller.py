from flask import jsonify
from firebase_admin import auth
import firebase_admin
from firebase_admin import credentials

def register_user(request):
    try:
        data = request.get_json()
        email = data.get("email")
        password = data.get("password")

        user = auth.create_user(email=email, password=password)
        return jsonify({"message": "Usuário registrado com sucesso", "uid": user.uid}), 201
    except Exception as e:
        return jsonify({"error": str(e)}), 400

def login_user(request):
    
    return jsonify({
        "error": "O login é feito no frontend com Firebase. Envie o token para /verify-token"
    }), 400

def verify_token(request):
    try:
        token = request.get_json().get("token")
        decoded_token = auth.verify_id_token(token)
        return jsonify({"status": "Token válido", "uid": decoded_token["uid"]}), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 401
