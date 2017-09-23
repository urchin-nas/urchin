import {NotificationManager} from 'react-notifications';

export const notifyInfo = (title, message) => {
    NotificationManager.info(message, title);
};

export const notifySuccess = (title, message) => {
    NotificationManager.success(message, title);
};

export const notifyWarning = (title, message) => {
    NotificationManager.warning(message, title);
};

export const notifyError = (title, message) => {
    NotificationManager.error(message, title);
};

export const notifyBackendError = (error) => {
    NotificationManager.error(error.message, error.errorCode);
};