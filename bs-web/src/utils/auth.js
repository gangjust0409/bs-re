import Cookie from 'js-cookie'

const TB_TOKEN = "tb_token";

export function setAuth(token) {
    Cookie.set(TB_TOKEN, token)
}

export function getAuth() {
    return Cookie.get(TB_TOKEN);
}

export function removeAuth() {
    Cookie.remove(TB_TOKEN);
}

