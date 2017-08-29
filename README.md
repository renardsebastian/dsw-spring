# ===============================
# dsw-spring
# ===============================

* não salvar a inscrição em bloco por conta do acesso concorrente

* implementar o DAO de inscrição

* revisar as tabelas de inscrição no Edital

* tela de edição de edital com dados básicos

* lista e editor de provas escritas em um edital

* implementação do comando de troca de senha

* seleção de edital no template de logado

* preparar a homepage com comandos de acordo com o Edital selecionado

* implementar a ação que retorna a lista de professores

* pegar modelos de relatórios com o Gleison

* além dos relatórios, temos as atas das Comissões, certo?

* conferir uso de senhas na configuração - chave e-mail, senha BD

* abrir uma conta SendGrid para o PPGI/UNIRIO

* rever comentários


# ===============================
# Tarefas do edital
# ===============================

Se o usuário for ADMIN:
- cadastro de editais

Se o edital selecionado estiver "em preparação":
- abrir edital

Se o edital selecionado estiver "aberto";
- encerrar inscrições

Se o edital selecionado estiver "em homologação"
- homologação de inscrições
- homologação de dispensa de provas
- relatório de inscrições homologadas (original)
- relatório de inscrições homologadas (recurso)
- relatório de candidatos dispensados (original)
- relatório de candidatos dispensados (recurso)
- encerramento de homologação

Se o edital selecionado estiver "em provas escritas"
- [relatórios anteriores]
- relatório de ficha para assinatura de presença nas provas
- indicação de presença por prova escrita
- lançamento de notas por prova escrita
- relatório de notas em provas (original)
- relatório de notas em provas (recurso)
- relatório de professores com pendências de lançamento de notas de prova escrita
- encerramento de provas escritas

Se o edital selecionado estiver "em alinhamento"
- [relatórios anteriores]
- relatório de ficha para assinatura de presença em prova oral
- indicação de presença na prova oral
- lançamento de notas de critérios de alinhamento
- relatório de notas de alinhamento (original)
- relatório de notas de alinhamento (recurso)
- relatório de professores com pendências de lançamento de notas de prova oral
- relatório de aprovação final
- encerramento de alinhamento

Se o edital selecionado estiver "finalizado"
- [relatórios anteriores]
