import { SessionInformation } from '../interfaces/sessionInformation.interface';

export const userRequestMock: SessionInformation = {
  token:
    'eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ5b2dhQHN0dWRpby5jb20iLCJpYXQiOjE3MzkyNjk0MDAsImV4cCI6MTczOTM1NTgwMH0.QBnFSkldurGOIjhHX-NYym9UXHCngbYp6ZdM_SsYnHxpGcUbQLsrGnunVrM6eLbtx2icTVtDK36Zj0yw0bdpfQ',
  type: 'Bearer',
  id: 3,
  username: 'yoga@studio.com',
  firstName: 'Admin',
  lastName: 'Admin',
  admin: true,
};
