import { BrowserRouter, Navigate, Route, Routes } from 'react-router';
import { LandingPage } from './landing/LandingPage';
import { ConfigurationPage } from './configure/ConfigurationPage';
import { QueuePage } from './queue/QueuePage';
import { ManageVisitorsPage } from './visitors/ManageVisitorsPage';

export const PAGES = {
    HOME: '/',
    CONFIGURE: '/configure',
    VISITORS: '/visitors',
    QUEUE: '/queue'
};

export const AppRoutes = () => {
    return (
        <BrowserRouter>
            <Routes>
                <Route path={PAGES.HOME} element={<LandingPage />} />
                <Route path={PAGES.QUEUE} element={<QueuePage />} />
                <Route path={PAGES.VISITORS} element={<ManageVisitorsPage />} />
                <Route path={PAGES.CONFIGURE} element={<ConfigurationPage />} />
                <Route path="*" element={<Navigate to={PAGES.HOME} />} />
            </Routes>
        </BrowserRouter>
    );
};
