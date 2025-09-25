import { BrowserRouter, Navigate, Route, Routes } from 'react-router';
import { LandingPage } from './landing/LandingPage';
import { ConfigurationPage } from './configure/ConfigurationPage';
import { QueuePage } from './queue/QueuePage';

export const PAGES = {
    HOME : '/',
    CONFIGURE: '/configure',
    QUEUE: '/queue'
}

export const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={PAGES.HOME} element={<LandingPage />} />
                <Route path={PAGES.CONFIGURE} element={<ConfigurationPage />} />
                <Route path={PAGES.QUEUE} element={<QueuePage />} />
                <Route path='*'  element={<Navigate to={PAGES.HOME} />} />
            </Routes>
        </BrowserRouter>
    );
};
