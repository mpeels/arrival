import { BrowserRouter, Navigate, Route, Routes } from 'react-router';
import { LandingPage } from './landing/LandingPage';
import { ManageProvidersPage } from './providers/ManageProvidersPage';
import { QueuePage } from './queue/QueuePage';
import { ManageVisitorsPage } from './visitors/ManageVisitorsPage';

export const PAGES = {
    HOME: '/',
    PROVIDERS: '/providers',
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
                <Route path={PAGES.PROVIDERS} element={<ManageProvidersPage />} />
                <Route path="*" element={<Navigate to={PAGES.HOME} />} />
            </Routes>
        </BrowserRouter>
    );
};
