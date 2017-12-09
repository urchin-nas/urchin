import {Actions} from '../constants'

export const toggleNavigation = (navigationVisible) => (dispatch) => {
    dispatch({
        type: Actions.Site.TOGGLE_NAVIGATION,
        data: !navigationVisible
    });
};