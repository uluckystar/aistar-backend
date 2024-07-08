#!/bin/bash

# 项目根目录
PROJECT_ROOT="src/main/java/com/localaihub/aistar/feature"

# 功能模块列表
FEATURES=("auth" "chat" "ollama" "user")

# 创建功能模块及其子包
for FEATURE in "${FEATURES[@]}"; do
  mkdir -p ${PROJECT_ROOT}/${FEATURE}/{controller,entity,mapper,repository,service,service/impl}
done

echo "Feature package structure created successfully."
