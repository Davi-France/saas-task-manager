export interface TaskInterface {
    id?: number;
    title: string;
    description: string;
    status: 'todo' | 'in-progress' | 'done';
}