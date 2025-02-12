#!/bin/bash

# Lista de microserviços (nomes das pastas)
SERVICES=("msclientes" "mseurekaserver" "msavaliadorcredito" "mscartoes" "msgateway")

# Construindo todas as imagens
for SERVICE in "${SERVICES[@]}"; do
    echo "🚀 Construindo imagem para $SERVICE..."

    # Verifica se o diretório do serviço existe
    if [ -d "./$SERVICE" ]; then
        docker build -t "$SERVICE:latest" -f "./$SERVICE/Dockerfile" "./$SERVICE"
        echo "✅ Imagem $SERVICE construída com sucesso!"
    else
        echo "❌ Erro: Diretório $SERVICE não encontrado!"
        continue
    fi
done