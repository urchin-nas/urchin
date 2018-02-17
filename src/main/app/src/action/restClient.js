import 'isomorphic-fetch'

const resolveResponse = (response) => {
    return response.json().then(json => {
        if (response.status >= 400) {
            return Promise.reject(json);
        } else {
            return Promise.resolve(json);
        }
    });
};

export const get = (url) => {
    return fetch(url)
        .then(response => {
            return resolveResponse(response);
        });
};

export const post = (url, body) => {
    return fetch(url, {
        credentials: 'include',
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(body)
    }).then(response => {
        return resolveResponse(response);
    });
};

export const del = (url) => {
    return fetch(url, {
        credentials: 'include',
        method: 'delete',
    }).then(response => {
        return resolveResponse(response);
    });
};
