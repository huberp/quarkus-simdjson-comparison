# quarkus-simdjson-comparison

A Quarkus project for comparing JSON parsing performance between different libraries:

1. **Jackson** (via `quarkus-resteasy-reactive-jackson`) - Standard JSON parser
2. **SimdJSON** - High-performance SIMD-based JSON parser
3. **Alibaba FastJSON2** - Fast JSON parser from Alibaba

## Endpoints

- `/user/standard` - Uses Jackson for JSON parsing
- `/user/simdjson` - Uses SimdJSON for JSON parsing
- `/user/fastjson2` - Uses Alibaba FastJSON2 for JSON parsing
