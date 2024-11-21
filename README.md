# SmartHome
    Camila Soares Pedra RM98246
    Gustavo Bertti RM552243
    Gustavo Macedo da Silva RM552333
    Rafael da Silva Camargo RM551127  
    
### Rodar aplicação em nuvem
    1 - Abrir o link https://acrwebappsmarthomeg.azurewebsites.net/
 
### Rodar aplicação local
    1 - Clonar o repositório para máquina local
    2 - Abrir o projeto
    3 - Substituir variavel OPEN_AI_KEY no application properties pela chave de API da OPEN AI
    4 - Abrir docker desktop
    5 - Digitar docker compose up --build no terminal do projeto 
    6 - Rodar a aplicação 
    
## Documentação da API com endpoints
    local:http://localhost:8080/docs
    nuvem: https://acrwebappsmarthomeg.azurewebsites.net/docs

### Como executar os testes
    Local:
        Fazer login para gerar o token JWT e colocar em cada requisição que for fazer. Endpoints liberados post (user, user/newPwd, login)
        Após login seguir mesmo passos abaixo porém utilizando localhost na url
        Estrutura dos JSON todas presentes na documentação da api
    Nuvem:
        Fazer as chamadas da API para os endpoints que estão listados na documentação https://acrwebappsmarthomeg.azurewebsites.net/docs
        seguir o formato dos campos e enviar as requisições
        Para cadastro do user, necessário informal email válido
        Para cadastro do device, no campo usagePeriod só são aceitos "MANHÃ", "TARDE", "NOITE", "DIA TODO", "MORNING", "AFTERNOON", "NIGHT", "ALL DAY"
        Para teste do endpoint report/userReport informar o sort para o pageable como createdAt
        Para teste do endpoint energyConsumption/consumptionByMonth informar a data no formato MM/yyyy 
    

