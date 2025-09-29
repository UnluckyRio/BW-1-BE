-- Script per la creazione del database PostgreSQL
-- Sistema di Gestione Trasporto Pubblico

-- Creazione del database (eseguire come superuser)
-- CREATE DATABASE trasporto_pubblico;
-- \c trasporto_pubblico;

-- Tabella Utenti
CREATE TABLE utenti (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    data_nascita DATE NOT NULL,
    email VARCHAR(150) UNIQUE,
    telefono VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Tessere
CREATE TABLE tessere (
    id BIGSERIAL PRIMARY KEY,
    numero_tessera VARCHAR(20) UNIQUE NOT NULL,
    utente_id BIGINT NOT NULL REFERENCES utenti(id) ON DELETE CASCADE,
    data_emissione DATE NOT NULL,
    data_scadenza DATE NOT NULL,
    attiva BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Punti di Emissione (classe padre)
CREATE TABLE punti_emissione (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(30) NOT NULL, -- 'DISTRIBUTORE' o 'RIVENDITORE'
    nome VARCHAR(100) NOT NULL,
    indirizzo VARCHAR(200),
    attivo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Distributori Automatici (estende punti_emissione)
CREATE TABLE distributori_automatici (
    id BIGINT PRIMARY KEY REFERENCES punti_emissione(id) ON DELETE CASCADE,
    stato_servizio VARCHAR(20) DEFAULT 'ATTIVO', -- 'ATTIVO' o 'FUORI_SERVIZIO'
    ultimo_controllo TIMESTAMP,
    codice_distributore VARCHAR(20) UNIQUE NOT NULL
);

-- Tabella Rivenditori Autorizzati (estende punti_emissione)
CREATE TABLE rivenditori_autorizzati (
    id BIGINT PRIMARY KEY REFERENCES punti_emissione(id) ON DELETE CASCADE,
    partita_iva VARCHAR(20) UNIQUE NOT NULL,
    ragione_sociale VARCHAR(150) NOT NULL,
    telefono VARCHAR(20),
    email VARCHAR(150)
);

-- Tabella Biglietti
CREATE TABLE biglietti (
    id BIGSERIAL PRIMARY KEY,
    codice_biglietto VARCHAR(30) UNIQUE NOT NULL,
    data_emissione TIMESTAMP NOT NULL,
    data_scadenza TIMESTAMP NOT NULL,
    prezzo DECIMAL(5,2) NOT NULL,
    validato BOOLEAN DEFAULT FALSE,
    data_validazione TIMESTAMP,
    punto_emissione_id BIGINT NOT NULL REFERENCES punti_emissione(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Abbonamenti
CREATE TABLE abbonamenti (
    id BIGSERIAL PRIMARY KEY,
    codice_abbonamento VARCHAR(30) UNIQUE NOT NULL,
    tipo VARCHAR(20) NOT NULL, -- 'SETTIMANALE' o 'MENSILE'
    data_emissione DATE NOT NULL,
    data_inizio_validita DATE NOT NULL,
    data_fine_validita DATE NOT NULL,
    prezzo DECIMAL(6,2) NOT NULL,
    tessera_id BIGINT NOT NULL REFERENCES tessere(id) ON DELETE CASCADE,
    punto_emissione_id BIGINT NOT NULL REFERENCES punti_emissione(id),
    attivo BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Mezzi (classe padre)
CREATE TABLE mezzi (
    id BIGSERIAL PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL, -- 'AUTOBUS' o 'TRAM'
    numero_mezzo VARCHAR(20) UNIQUE NOT NULL,
    capienza INTEGER NOT NULL,
    stato VARCHAR(20) DEFAULT 'IN_SERVIZIO', -- 'IN_SERVIZIO' o 'IN_MANUTENZIONE'
    anno_immatricolazione INTEGER,
    modello VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Autobus (estende mezzi)
CREATE TABLE autobus (
    id BIGINT PRIMARY KEY REFERENCES mezzi(id) ON DELETE CASCADE,
    numero_porte INTEGER DEFAULT 2,
    accessibile_disabili BOOLEAN DEFAULT FALSE,
    alimentazione VARCHAR(20) DEFAULT 'DIESEL' -- 'DIESEL', 'METANO', 'ELETTRICO'
);

-- Tabella Tram (estende mezzi)
CREATE TABLE tram (
    id BIGINT PRIMARY KEY REFERENCES mezzi(id) ON DELETE CASCADE,
    numero_carrozze INTEGER DEFAULT 1,
    lunghezza_metri DECIMAL(4,2),
    tipo_alimentazione VARCHAR(20) DEFAULT 'ELETTRICO'
);

-- Tabella Tratte
CREATE TABLE tratte (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    zona_partenza VARCHAR(100) NOT NULL,
    capolinea VARCHAR(100) NOT NULL,
    tempo_previsto_minuti INTEGER NOT NULL,
    distanza_km DECIMAL(5,2),
    attiva BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Periodi di Servizio/Manutenzione
CREATE TABLE periodi_servizio (
    id BIGSERIAL PRIMARY KEY,
    mezzo_id BIGINT NOT NULL REFERENCES mezzi(id) ON DELETE CASCADE,
    tipo_periodo VARCHAR(20) NOT NULL, -- 'SERVIZIO' o 'MANUTENZIONE'
    data_inizio TIMESTAMP NOT NULL,
    data_fine TIMESTAMP,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Vidimazioni
CREATE TABLE vidimazioni (
    id BIGSERIAL PRIMARY KEY,
    biglietto_id BIGINT NOT NULL REFERENCES biglietti(id) ON DELETE CASCADE,
    mezzo_id BIGINT NOT NULL REFERENCES mezzi(id) ON DELETE CASCADE,
    data_vidimazione TIMESTAMP NOT NULL,
    tratta_id BIGINT REFERENCES tratte(id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabella Percorrenze Tratte
CREATE TABLE percorrenze_tratte (
    id BIGSERIAL PRIMARY KEY,
    mezzo_id BIGINT NOT NULL REFERENCES mezzi(id) ON DELETE CASCADE,
    tratta_id BIGINT NOT NULL REFERENCES tratte(id) ON DELETE CASCADE,
    data_percorrenza DATE NOT NULL,
    ora_partenza TIME NOT NULL,
    ora_arrivo TIME,
    tempo_effettivo_minuti INTEGER,
    completata BOOLEAN DEFAULT FALSE,
    note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Indici per migliorare le performance
CREATE INDEX idx_tessere_numero ON tessere(numero_tessera);
CREATE INDEX idx_tessere_utente ON tessere(utente_id);
CREATE INDEX idx_biglietti_codice ON biglietti(codice_biglietto);
CREATE INDEX idx_biglietti_emissione ON biglietti(data_emissione);
CREATE INDEX idx_abbonamenti_tessera ON abbonamenti(tessera_id);
CREATE INDEX idx_abbonamenti_validita ON abbonamenti(data_inizio_validita, data_fine_validita);
CREATE INDEX idx_vidimazioni_data ON vidimazioni(data_vidimazione);
CREATE INDEX idx_vidimazioni_mezzo ON vidimazioni(mezzo_id);
CREATE INDEX idx_percorrenze_data ON percorrenze_tratte(data_percorrenza);
CREATE INDEX idx_periodi_servizio_mezzo ON periodi_servizio(mezzo_id);

-- Trigger per aggiornare updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TRIGGER update_utenti_updated_at BEFORE UPDATE ON utenti 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tessere_updated_at BEFORE UPDATE ON tessere 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_punti_emissione_updated_at BEFORE UPDATE ON punti_emissione 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_mezzi_updated_at BEFORE UPDATE ON mezzi 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_tratte_updated_at BEFORE UPDATE ON tratte 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Inserimento dati di esempio
INSERT INTO utenti (nome, cognome, data_nascita, email, telefono) VALUES
('Mario', 'Rossi', '1985-03-15', 'mario.rossi@email.com', '3331234567'),
('Giulia', 'Bianchi', '1990-07-22', 'giulia.bianchi@email.com', '3339876543'),
('Luca', 'Verdi', '1988-11-10', 'luca.verdi@email.com', '3335555555');

INSERT INTO punti_emissione (tipo, nome, indirizzo, attivo) VALUES
('DISTRIBUTORE', 'Distributore Stazione Centrale', 'Piazza Stazione 1', TRUE),
('DISTRIBUTORE', 'Distributore Piazza Duomo', 'Piazza Duomo 5', TRUE),
('RIVENDITORE', 'Tabaccheria Centrale', 'Via Roma 15', TRUE),
('RIVENDITORE', 'Edicola Stazione', 'Viale Stazione 8', TRUE);

INSERT INTO distributori_automatici (id, stato_servizio, codice_distributore) VALUES
(1, 'ATTIVO', 'DIST001'),
(2, 'ATTIVO', 'DIST002');

INSERT INTO rivenditori_autorizzati (id, partita_iva, ragione_sociale, telefono, email) VALUES
(3, '12345678901', 'Tabaccheria Centrale SNC', '0123456789', 'info@tabaccheria.com'),
(4, '10987654321', 'Edicola Stazione SRL', '0123987654', 'edicola@stazione.com');

INSERT INTO mezzi (tipo, numero_mezzo, capienza, stato, anno_immatricolazione, modello) VALUES
('AUTOBUS', 'BUS001', 80, 'IN_SERVIZIO', 2020, 'Mercedes Citaro'),
('AUTOBUS', 'BUS002', 90, 'IN_SERVIZIO', 2021, 'Iveco Urbanway'),
('TRAM', 'TRAM001', 120, 'IN_SERVIZIO', 2019, 'Alstom Citadis'),
('TRAM', 'TRAM002', 150, 'IN_MANUTENZIONE', 2018, 'Siemens Combino');

INSERT INTO autobus (id, numero_porte, accessibile_disabili, alimentazione) VALUES
(1, 3, TRUE, 'METANO'),
(2, 2, TRUE, 'ELETTRICO');

INSERT INTO tram (id, numero_carrozze, lunghezza_metri, tipo_alimentazione) VALUES
(3, 2, 32.50, 'ELETTRICO'),
(4, 3, 45.00, 'ELETTRICO');

INSERT INTO tratte (nome, zona_partenza, capolinea, tempo_previsto_minuti, distanza_km) VALUES
('Linea 1 - Centro-Periferia', 'Stazione Centrale', 'Quartiere Residenziale', 25, 8.5),
('Linea 2 - Università-Ospedale', 'Campus Universitario', 'Ospedale Civile', 18, 6.2),
('Linea T1 - Tram Centro', 'Piazza Duomo', 'Stazione FS', 12, 4.1);