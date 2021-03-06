var config = require('config'),
    path = require('path'),
    DailyRotateFile = require('winston-daily-rotate-file');

const { logLevel } = require('kafkajs');
const { createLogger, format, transports } = require('winston');
const { combine, timestamp, label, printf, splat } = format;

const toWinstonLogLevel = level => {
    switch(level) {
        case logLevel.ERROR:
        case logLevel.NOTHING:
            return 'error'
        case logLevel.WARN:
            return 'warn'
        case logLevel.INFO:
            return 'info'
        case logLevel.DEBUG:
            return 'debug'
    }
}

var loggerTransports = [];

if (process.env.NODE_ENV !== 'production' || process.env.DOCKER_MODE === 'true') {
    loggerTransports.push(new transports.Console({
        handleExceptions: true
    }));
} else {
    var filename = path.join(config.get('logger.path'), config.get('logger.filename'));
    var transport = new (DailyRotateFile)({
        filename: filename,
        datePattern: 'YYYY-MM-DD-HH',
        zippedArchive: true,
        maxSize: '20m',
        maxFiles: '14d',
        handleExceptions: true
    });
    loggerTransports.push(transport);
}

const tbFormat = printf(info => {
    return `${info.timestamp} [${info.label}] ${info.level.toUpperCase()}: ${info.message}`;
});

function _logger(moduleLabel) {
    return createLogger({
        level: config.get('logger.level'),
        format:combine(
            splat(),
            label({ label: moduleLabel }),
            timestamp({format: 'YYYY-MM-DD HH:mm:ss,SSS'}),
            tbFormat
        ),
        transports: loggerTransports
    });
}

const KafkaJsWinstonLogCreator = logLevel => {
    const logger = createLogger({
        level: toWinstonLogLevel(logLevel),
        format:combine(
            splat(),
            label({ label: 'kafkajs' }),
            timestamp({format: 'YYYY-MM-DD HH:mm:ss,SSS'}),
            printf(info => {
                var res = `${info.timestamp} [${info.label}] ${info.level.toUpperCase()}: ${info.message}`;
                if (info.extra) {
                    res +=`: ${JSON.stringify(info.extra)}`;
                }
                return res;
              }
            )
        ),
        transports: loggerTransports
    });

    return ({ namespace, level, label, log }) => {
        const { message, ...extra } = log;
        logger.log({
            level: toWinstonLogLevel(level),
            message,
            extra,
        });
    }
}

module.exports = {_logger, KafkaJsWinstonLogCreator};
