import {Actions} from '../constants'
import {get} from './restClient'

const {Users} = Actions;

export const getUsers = () => (dispatch) => {
    dispatch({
        type: Users.GET_USERS
    });
    return get('/api/users')
        .then(json => dispatch({
            type: Users.RECEIVED_USERS,
            data: json
        }))
};