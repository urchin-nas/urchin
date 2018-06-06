import 'isomorphic-fetch'

const resolveResponse = (response) => {
    return response.json().then(json => {
        if (response.status >= 400) {
            json.httpStatus = response.status;
            return Promise.reject(json);
        } else {
            return Promise.resolve(json);
        }
    });
};

export const get = (url) => {
    return fetch(url, {
        method: 'get',
        credentials: 'include'
    })
        .then(response => {
            return resolveResponse(response);
        });
};

export const post = (url, body) => {
    return fetch(url, {
        method: 'post',
        credentials: 'include',
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
        method: 'delete',
        credentials: 'include',
    }).then(response => {
        return resolveResponse(response);
    });
};
