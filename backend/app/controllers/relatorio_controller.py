from google import genai
import os
from dotenv import load_dotenv
import json
from google.genai import types

load_dotenv()

client = genai.Client(api_key=os.getenv("API_KEY"))

def gerar_relatorio():
    with open("userDataMock.json", "r", encoding="utf-8") as f:
        user_data = json.load(f)

    dados_formatados = json.dumps(user_data, indent=2, ensure_ascii=False)

    prompt = f"""Compare a semana atual com a semana anterior, levando em consideração os dados das metas do usuário.

sempre comece o relatório com a seguinte frase: "Olá {user_data.get('name')}, vamos dar uma olhada no seu relatório desta semana?"

dados do usuário:
{dados_formatados}
"""

    response = client.models.generate_content(
        model="gemini-2.0-flash",
        config=types.GenerateContentConfig(
            system_instruction="Você é um instrutor de bem estar, possui uma fala direta e profissional.",
            temperature=0.1,
            max_output_tokens=400,
            candidateCount=1,
        ),
        contents=prompt
    )
    return response.text
