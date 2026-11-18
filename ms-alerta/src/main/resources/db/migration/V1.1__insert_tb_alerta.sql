INSERT INTO tb_alerta (idoso_id, tipo, gravidade, origem, mensagem, data_criacao, status, status_sincronizado) VALUES
    (1, 'MEDICAMENTO', 'BAIXA', 'CONTATO', 'Esqueceu uma dose do medicamento da manhã, já foi orientado.', '2026-11-10 08:30:00', 'RESOLVIDO', TRUE),
    (3, 'HUMOR_RUIM', 'MEDIA', 'IDOSO', 'Relatou desânimo e pouco apetite nos últimos dias.', '2026-11-16 14:00:00', 'ABERTO', TRUE),
    (4, 'SEM_CHECKIN', 'ALTA', 'SISTEMA', 'Sem check-in há mais de 24 horas.', '2026-11-17 09:15:00', 'ABERTO', TRUE);