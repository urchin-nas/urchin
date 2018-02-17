import {Actions} from '../constants'

export const toggleNavigation = (navigationVisible) => (dispatch) => {
    return dispatch({
        type: Actions.Site.TOGGLE_NAVIGATION,
        data: !navigationVisible
    });
};