import fetch from 'isomorphic-fetch'

export const get = (url) => {
    return fetch(url)
        .then(response => {
            if (response.status >= 400) {
                throw new Error("Bad response from server");
            }
            return response.json();
        });
};

export const post = (url, body) => {
    console.log(body);
    return fetch(url, {
        credentials: 'include',
        method: 'post',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(body)
    }).then(response => {
        if (response.status >= 400) {
            throw new Error("Bad response from server");
        }
        return response.json();
    });
};
