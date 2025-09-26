export type Visitor = {
    id: number;
    firstName: string;
    lastName: string;
    number: number;
    status: 'WAITING' | 'IN_PROGRESS' | 'COMPLETED';
    provider: number;
    arrivalTime: string;
    callTime: string;
    completeTime: string;
};

export type VisitorQueue = {
    entries: QueueEntry[];
};

export type QueueEntry = {
    firstName: string;
    lastName: string;
    number: number;
    provider: string;
};
