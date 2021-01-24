const {
    PHASE_DEVELOPMENT_SERVER,
    PHASE_PRODUCTION_BUILD,
} = require('next/constants')

module.exports = (phase) => {
    const isDev = phase === PHASE_DEVELOPMENT_SERVER
    const isProd = phase === PHASE_PRODUCTION_BUILD && process.env.STAGING !== '1'
    const isStaging = phase === PHASE_PRODUCTION_BUILD && process.env.STAGING === '1'

    console.log(`isDev:${isDev}  isProd:${isProd}   isStaging:${isStaging}`)

    const env = {
        GOOGLE_ID: (() => {
            return 'setYourGoogleClientIdHere'
        })(),
        CLIENT_URL: (() => {
            if (isProd) {
                return 'http://googleflashcards.us';
            }
            return 'http://localhost:3000';
        })(),
        SERVER_API_URL: (() => {
            if (isProd) {
                return 'http://googleflashcards.us:8080/api';
            }

            return 'http://localhost:8080/api';
        })(),
    }

    return {
        env,
    }
}